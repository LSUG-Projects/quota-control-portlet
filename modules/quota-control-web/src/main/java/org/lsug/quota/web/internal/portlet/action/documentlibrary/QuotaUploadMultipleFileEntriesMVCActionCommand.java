/**
 * Copyright (c) 2013-present Liferay Spain User Group All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package org.lsug.quota.web.internal.portlet.action.documentlibrary;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.WindowState;
import javax.servlet.http.HttpServletResponse;

import org.lsug.quota.exception.QuotaExceededException;
import org.lsug.quota.util.PortletKeys;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.liferay.asset.kernel.exception.AssetCategoryException;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.document.library.kernel.antivirus.AntivirusScannerException;
import com.liferay.document.library.kernel.exception.DuplicateFileEntryException;
import com.liferay.document.library.kernel.exception.FileExtensionException;
import com.liferay.document.library.kernel.exception.FileNameException;
import com.liferay.document.library.kernel.exception.FileSizeException;
import com.liferay.document.library.kernel.exception.InvalidFileEntryTypeException;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.document.library.kernel.util.DLUtil;
import com.liferay.document.library.web.constants.DLPortletKeys;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.UploadException;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TempFileEntryUtil;
import com.liferay.portal.kernel.util.TextFormatter;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

/**
 * 
 * @author Juan Gonzalez
 *
 */
@Component(
		property = {
			"javax.portlet.name=" + DLPortletKeys.DOCUMENT_LIBRARY,
			"javax.portlet.name=" + DLPortletKeys.DOCUMENT_LIBRARY_ADMIN,
			"javax.portlet.name=" + DLPortletKeys.MEDIA_GALLERY_DISPLAY,
			"mvc.command.name=/document_library/upload_multiple_file_entries",
			"service.ranking:Integer=100"
		},
		service = MVCActionCommand.class
	)
public class QuotaUploadMultipleFileEntriesMVCActionCommand extends BaseMVCActionCommand {

		public static final String TEMP_FOLDER_NAME ="com.liferay.document.library.web.internal.portlet.action.EditFileEntryMVCActionCommand";

		protected void addMultipleFileEntries(
				PortletConfig portletConfig, ActionRequest actionRequest,
				ActionResponse actionResponse)
			throws Exception {

			List<KeyValuePair> validFileNameKVPs = new ArrayList<>();
			List<KeyValuePair> invalidFileNameKVPs = new ArrayList<>();

			String[] selectedFileNames = ParamUtil.getParameterValues(
				actionRequest, "selectedFileName", new String[0], false);

			for (String selectedFileName : selectedFileNames) {
				addMultipleFileEntries(
					portletConfig, actionRequest, actionResponse, selectedFileName,
					validFileNameKVPs, invalidFileNameKVPs);
			}

			JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

			for (KeyValuePair validFileNameKVP : validFileNameKVPs) {
				String fileName = validFileNameKVP.getKey();
				String originalFileName = validFileNameKVP.getValue();

				JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

				jsonObject.put("added", Boolean.TRUE);
				jsonObject.put("fileName", fileName);
				jsonObject.put("originalFileName", originalFileName);

				jsonArray.put(jsonObject);
			}

			for (KeyValuePair invalidFileNameKVP : invalidFileNameKVPs) {
				String fileName = invalidFileNameKVP.getKey();
				String errorMessage = invalidFileNameKVP.getValue();

				JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

				jsonObject.put("added", Boolean.FALSE);
				jsonObject.put("errorMessage", errorMessage);
				jsonObject.put("fileName", fileName);
				jsonObject.put("originalFileName", fileName);

				jsonArray.put(jsonObject);
			}

			JSONPortletResponseUtil.writeJSON(
				actionRequest, actionResponse, jsonArray);
		}

		protected void addMultipleFileEntries(
				PortletConfig portletConfig, ActionRequest actionRequest,
				ActionResponse actionResponse, String selectedFileName,
				List<KeyValuePair> validFileNameKVPs,
				List<KeyValuePair> invalidFileNameKVPs)
			throws Exception {

			ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

			long repositoryId = ParamUtil.getLong(actionRequest, "repositoryId");
			long folderId = ParamUtil.getLong(actionRequest, "folderId");
			String description = ParamUtil.getString(actionRequest, "description");
			String changeLog = ParamUtil.getString(actionRequest, "changeLog");

			FileEntry tempFileEntry = null;

			try {
				tempFileEntry = TempFileEntryUtil.getTempFileEntry(
					themeDisplay.getScopeGroupId(), themeDisplay.getUserId(),
					TEMP_FOLDER_NAME, selectedFileName);

				String originalSelectedFileName =
					TempFileEntryUtil.getOriginalTempFileName(
						tempFileEntry.getFileName());

				String uniqueFileName = DLUtil.getUniqueFileName(
					tempFileEntry.getGroupId(), folderId, originalSelectedFileName);

				String mimeType = tempFileEntry.getMimeType();
				InputStream inputStream = tempFileEntry.getContentStream();
				long size = tempFileEntry.getSize();

				ServiceContext serviceContext = ServiceContextFactory.getInstance(
					DLFileEntry.class.getName(), actionRequest);

				_dlAppService.addFileEntry(
					repositoryId, folderId, uniqueFileName, mimeType,
					uniqueFileName, description, changeLog, inputStream, size,
					serviceContext);

				validFileNameKVPs.add(
					new KeyValuePair(uniqueFileName, selectedFileName));

				return;
			}
			catch (Exception e) {
				String errorMessage = getAddMultipleFileEntriesErrorMessage(
					portletConfig, actionRequest, actionResponse, e);

				invalidFileNameKVPs.add(
					new KeyValuePair(selectedFileName, errorMessage));
			}
			finally {
				if (tempFileEntry != null) {
					TempFileEntryUtil.deleteTempFileEntry(
						tempFileEntry.getFileEntryId());
				}
			}
		}

		protected void addTempFileEntry(
				ActionRequest actionRequest, ActionResponse actionResponse)
			throws Exception {

			UploadPortletRequest uploadPortletRequest =
				_portal.getUploadPortletRequest(actionRequest);

			ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

			long folderId = ParamUtil.getLong(uploadPortletRequest, "folderId");
			String sourceFileName = uploadPortletRequest.getFileName("file");

			InputStream inputStream = null;

			try {
				String tempFileName = TempFileEntryUtil.getTempFileName(
					sourceFileName);

				inputStream = uploadPortletRequest.getFileAsStream("file");

				String mimeType = uploadPortletRequest.getContentType("file");

				FileEntry fileEntry = _dlAppService.addTempFileEntry(
					themeDisplay.getScopeGroupId(), folderId, TEMP_FOLDER_NAME,
					tempFileName, inputStream, mimeType);

				JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

				jsonObject.put("groupId", fileEntry.getGroupId());
				jsonObject.put("name", fileEntry.getTitle());
				jsonObject.put("title", sourceFileName);
				jsonObject.put("uuid", fileEntry.getUuid());

				JSONPortletResponseUtil.writeJSON(
					actionRequest, actionResponse, jsonObject);
			}
			finally {
				StreamUtil.cleanUp(inputStream);
			}
		}

		@Override
		protected void doProcessAction(
				ActionRequest actionRequest, ActionResponse actionResponse)
			throws Exception {

			String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

			PortletConfig portletConfig = getPortletConfig(actionRequest);

			try {
				UploadException uploadException =
					(UploadException)actionRequest.getAttribute(
						WebKeys.UPLOAD_EXCEPTION);

				if (uploadException != null) {
						mvcActionCommand.processAction(actionRequest, actionResponse);
						return;
				}
				else if (cmd.equals(Constants.ADD_MULTIPLE)) {
					addMultipleFileEntries(
						portletConfig, actionRequest, actionResponse);

					hideDefaultSuccessMessage(actionRequest);
				}
				else if (cmd.equals(Constants.ADD_TEMP)) {
					addTempFileEntry(actionRequest, actionResponse);
					actionResponse.setRenderParameter("mvcPath", "/null.jsp");
				}
				else {
					mvcActionCommand.processAction(actionRequest, actionResponse);
					return;
				}
				
				WindowState windowState = actionRequest.getWindowState();

				if (windowState.equals(LiferayWindowState.POP_UP)) {
					String redirect = ParamUtil.getString(
							actionRequest, "redirect");
				
					if (windowState.equals(LiferayWindowState.POP_UP)) {
						redirect = _portal.escapeRedirect(
								ParamUtil.getString(actionRequest, "redirect"));

						if (Validator.isNotNull(redirect)) {
							actionRequest.setAttribute(
									WebKeys.REDIRECT, redirect);
						}
					}					
				}
			}
			catch (Exception e) {
				handleUploadException(
					portletConfig, actionRequest, actionResponse, cmd, e);
			}
		}

		protected String getAddMultipleFileEntriesErrorMessage(
				PortletConfig portletConfig, ActionRequest actionRequest,
				ActionResponse actionResponse, Exception e)
			throws Exception {

			String errorMessage = null;

			ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

			if (e instanceof AntivirusScannerException) {
				AntivirusScannerException ase = (AntivirusScannerException)e;

				errorMessage = themeDisplay.translate(ase.getMessageKey());
			}
			else if (e instanceof AssetCategoryException) {
				AssetCategoryException ace = (AssetCategoryException)e;

				AssetVocabulary assetVocabulary = ace.getVocabulary();

				String vocabularyTitle = StringPool.BLANK;

				if (assetVocabulary != null) {
					vocabularyTitle = assetVocabulary.getTitle(
						themeDisplay.getLocale());
				}

				if (ace.getType() == AssetCategoryException.AT_LEAST_ONE_CATEGORY) {
					errorMessage = themeDisplay.translate(
						"please-select-at-least-one-category-for-x",
						vocabularyTitle);
				}
				else if (ace.getType() ==
							AssetCategoryException.TOO_MANY_CATEGORIES) {

					errorMessage = themeDisplay.translate(
						"you-cannot-select-more-than-one-category-for-x",
						vocabularyTitle);
				}
			}
			else if (e instanceof DuplicateFileEntryException) {
				errorMessage = themeDisplay.translate(
					"the-folder-you-selected-already-has-an-entry-with-this-" +
						"name.-please-select-a-different-folder");
			}
			else if (e instanceof FileExtensionException) {
				errorMessage = themeDisplay.translate(
					"please-enter-a-file-with-a-valid-extension-x",
					StringUtil.merge(
						getAllowedFileExtensions(
							portletConfig, actionRequest, actionResponse)));
			}
			else if (e instanceof FileNameException) {
				errorMessage = themeDisplay.translate(
					"please-enter-a-file-with-a-valid-file-name");
			}
			else if (e instanceof FileSizeException) {
				long fileMaxSize = PrefsPropsUtil.getLong(
					PropsKeys.DL_FILE_MAX_SIZE);

				if (fileMaxSize == 0) {
					fileMaxSize = PrefsPropsUtil.getLong(
						PropsKeys.UPLOAD_SERVLET_REQUEST_IMPL_MAX_SIZE);
				}

				errorMessage = themeDisplay.translate(
					"please-enter-a-file-with-a-valid-file-size-no-larger-than-x",
					TextFormatter.formatStorageSize(
						fileMaxSize, themeDisplay.getLocale()));
			}
			else if (e instanceof InvalidFileEntryTypeException) {
				errorMessage = themeDisplay.translate(
					"the-document-type-you-selected-is-not-valid-for-this-folder");
			}
			else if (e instanceof InvalidFileEntryTypeException) {
				errorMessage = themeDisplay.translate(
					"the-document-type-you-selected-is-not-valid-for-this-folder");
			}
			else if (e instanceof QuotaExceededException) {
				ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
						"content.Language", themeDisplay.getLocale(), getClass());

				errorMessage = ResourceBundleUtil.getString(
						resourceBundle, "quota-exceeded-error");
			}
			else {
				errorMessage = themeDisplay.translate(
					"an-unexpected-error-occurred-while-saving-your-document");
			}

			return errorMessage;
		}

		protected String[] getAllowedFileExtensions(
				PortletConfig portletConfig, PortletRequest portletRequest,
				PortletResponse portletResponse)
			throws PortalException {

			String portletName = portletConfig.getPortletName();

			if (!portletName.equals(DLPortletKeys.MEDIA_GALLERY_DISPLAY)) {
				return PrefsPropsUtil.getStringArray(
					PropsKeys.DL_FILE_EXTENSIONS, StringPool.COMMA);
			}
			else {
				
				//TODO DLPortletInstanceSettings is an internal class
				//We cannot use it here as it does in documentlibrary module
				Set<String> extensions = new HashSet<>();

				return extensions.toArray(new String[extensions.size()]);
			}
		}

		protected void handleUploadException(
				PortletConfig portletConfig, ActionRequest actionRequest,
				ActionResponse actionResponse, String cmd, Exception e)
			throws Exception {

			if (e instanceof QuotaExceededException) {

				if (cmd.equals(Constants.ADD_TEMP)) {
					hideDefaultErrorMessage(actionRequest);
				}

				HttpServletResponse response = _portal.getHttpServletResponse(
					actionResponse);

				response.setContentType(ContentTypes.TEXT_HTML);
				response.setStatus(HttpServletResponse.SC_OK);

				String errorMessage = StringPool.BLANK;
				int errorType = PortletKeys.SC_QUOTA_EXCEEDED_ERROR_TYPE;

				ThemeDisplay themeDisplay =
						(ThemeDisplay)actionRequest.getAttribute(
								WebKeys.THEME_DISPLAY);

				ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
						"content.Language", themeDisplay.getLocale(), getClass());

				errorMessage = ResourceBundleUtil.getString(
						resourceBundle, "quota-exceeded-error");

				JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

				jsonObject.put("message", errorMessage);
				jsonObject.put("status", errorType);

				JSONPortletResponseUtil.writeJSON(
						actionRequest, actionResponse, jsonObject);

				SessionErrors.add(actionRequest, e.getClass());
				
			}
			else {
				throw e;	
			}
		}


		@Reference(unbind = "-")
		protected void setDLAppService(DLAppService dlAppService) {
			_dlAppService = dlAppService;
		}

		private DLAppService _dlAppService;

		@Reference
		private Portal _portal;
		
	    @Reference(target = "(component.name=com.liferay.document.library.web.internal.portlet.action.EditFileEntryMVCActionCommand)")
	    protected MVCActionCommand mvcActionCommand;


}

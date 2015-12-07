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

package org.lsug.quota;

import com.liferay.portal.DuplicateLockException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.servlet.ServletResponseConstants;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.struts.BaseStrutsPortletAction;
import com.liferay.portal.kernel.struts.StrutsPortletAction;
import com.liferay.portal.kernel.upload.LiferayFileItemException;
import com.liferay.portal.kernel.upload.UploadException;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TempFileUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Layout;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portlet.asset.AssetCategoryException;
import com.liferay.portlet.asset.AssetTagException;
import com.liferay.portlet.asset.model.AssetVocabulary;
import com.liferay.portlet.assetpublisher.util.AssetPublisherUtil;
import com.liferay.portlet.documentlibrary.DuplicateFileException;
import com.liferay.portlet.documentlibrary.DuplicateFolderNameException;
import com.liferay.portlet.documentlibrary.FileExtensionException;
import com.liferay.portlet.documentlibrary.FileMimeTypeException;
import com.liferay.portlet.documentlibrary.FileNameException;
import com.liferay.portlet.documentlibrary.FileSizeException;
import com.liferay.portlet.documentlibrary.InvalidFileEntryTypeException;
import com.liferay.portlet.documentlibrary.InvalidFileVersionException;
import com.liferay.portlet.documentlibrary.NoSuchFileEntryException;
import com.liferay.portlet.documentlibrary.NoSuchFolderException;
import com.liferay.portlet.documentlibrary.SourceFileNameException;
import com.liferay.portlet.documentlibrary.antivirus.AntivirusScannerException;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLAppServiceUtil;
import com.liferay.portlet.documentlibrary.util.DLUtil;
import com.liferay.portlet.dynamicdatamapping.StorageFieldRequiredException;

import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileUploadBase.IOFileUploadException;

import org.lsug.quota.util.QuotaUtil;

/**
 *  Document library override for some file entry struts actions
 *  @author LSUG
 *
 */
public class QuotaEditFileEntryAction extends BaseStrutsPortletAction {

	public static final String PORTLET_STRUTS_FORWARD = "PORTLET_STRUTS_FORWARD";

	public static final String COMMON_NULL = "/common/null.jsp";
	public static final String TEMP_RANDOM_SUFFIX = "--tempRandomSuffix--";

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
		else if (e instanceof DuplicateFileException) {
			errorMessage = themeDisplay.translate(
				"the-folder-you-selected-already-has-an-entry-with-this-name." +
					"-please-select-a-different-folder");
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
			long maxSizeMB = PrefsPropsUtil.getLong(
				PropsKeys.DL_FILE_MAX_SIZE) / 1024 / 1024;

			errorMessage = themeDisplay.translate(
				"file-size-is-larger-than-x-megabytes", maxSizeMB);
		}
		else if (e instanceof InvalidFileEntryTypeException) {
			errorMessage = themeDisplay.translate(
				"the-document-type-you-selected-is-not-valid-for-this-folder");
		}
		else if (e instanceof QuotaExceededException) {
			errorMessage = QuotaUtil.getResource(
					actionRequest, "quota-exceeded-error");
		}
		else {
			errorMessage = themeDisplay.translate(
				"an-unexpected-error-occurred-while-saving-your-document");
		}

		return errorMessage;
	}

	@Override
	public void processAction(StrutsPortletAction originalStrutsPortletAction,
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD_TEMP)) {
				addTempFileEntry(actionRequest, actionResponse);
				setForward(actionRequest, COMMON_NULL);
			}
			else if (cmd.equals(Constants.ADD_MULTIPLE)) {
					addMultipleFileEntries(
						portletConfig, actionRequest, actionResponse);

					//hideDefaultSuccessMessage(actionRequest);
			}
			else {
				originalStrutsPortletAction.processAction(
					originalStrutsPortletAction, portletConfig, actionRequest,
					actionResponse);
			}
		}
		catch (Exception e) {
			handleUploadException(
				portletConfig, actionRequest, actionResponse, cmd, e);
		}
	}

	@Override
	public String render(StrutsPortletAction originalStrutsPortletAction,
			PortletConfig portletConfig, RenderRequest renderRequest,
			RenderResponse renderResponse) throws Exception {

		return originalStrutsPortletAction.render(originalStrutsPortletAction,
				portletConfig, renderRequest, renderResponse);
	}

	@Override
	public void serveResource(StrutsPortletAction originalStrutsPortletAction,
			PortletConfig portletConfig, ResourceRequest resourceRequest,
			ResourceResponse resourceResponse) throws Exception {
		originalStrutsPortletAction.serveResource(originalStrutsPortletAction,
				portletConfig, resourceRequest, resourceResponse);
	}

	protected String[] getAllowedFileExtensions(
			PortletConfig portletConfig, PortletRequest portletRequest,
			PortletResponse portletResponse)
		throws PortalException, SystemException {

		String portletName = portletConfig.getPortletName();

		if (!portletName.equals(PortletKeys.MEDIA_GALLERY_DISPLAY)) {
			return PrefsPropsUtil.getStringArray(
				PropsKeys.DL_FILE_EXTENSIONS, StringPool.COMMA);
		}
		else {
			PortletPreferences portletPreferences = getStrictPortletSetup(
				portletRequest);

			if (portletPreferences == null) {
				portletPreferences = portletRequest.getPreferences();
			}

			Set<String> extensions = new HashSet<String>();

			String[] mimeTypes = DLUtil.getMediaGalleryMimeTypes(
				portletPreferences, portletRequest);

			for (String mimeType : mimeTypes) {
				extensions.addAll(MimeTypesUtil.getExtensions(mimeType));
			}

			return extensions.toArray(new String[extensions.size()]);
		}
	}

	protected PortletPreferences getStrictPortletSetup(
			Layout layout, String portletId)
		throws PortalException, SystemException {

		if (Validator.isNull(portletId)) {
			return null;
		}

		PortletPreferences portletPreferences =
			PortletPreferencesFactoryUtil.getStrictPortletSetup(
				layout, portletId);

		if (portletPreferences.getClass().getName().endsWith("StrictPortletPreferencesImpl")) {
			throw new PrincipalException();
		}

		return portletPreferences;
	}

	protected PortletPreferences getStrictPortletSetup(
			PortletRequest portletRequest)
		throws PortalException, SystemException {

		String portletResource = ParamUtil.getString(
			portletRequest, "portletResource");

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		return getStrictPortletSetup(themeDisplay.getLayout(), portletResource);
	}

	protected void writeJSON(
			PortletRequest portletRequest, ActionResponse actionResponse,
			Object json)
		throws IOException {

		HttpServletResponse response = PortalUtil.getHttpServletResponse(
			actionResponse);

		response.setContentType(ContentTypes.APPLICATION_JSON);

		ServletResponseUtil.write(response, json.toString());

		response.flushBuffer();

		setForward(portletRequest, COMMON_NULL);
	}

	protected void setForward(PortletRequest portletRequest, String forward) {
		portletRequest.setAttribute(getForwardKey(portletRequest), forward);
	}

	public static String getForwardKey(PortletRequest portletRequest) {
		String portletId = (String)portletRequest.getAttribute(
			WebKeys.PORTLET_ID);

		String portletNamespace = PortalUtil.getPortletNamespace(portletId);

		return portletNamespace.concat(PORTLET_STRUTS_FORWARD);
	}

	protected void addMultipleFileEntries(
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		List<KeyValuePair> validFileNameKVPs = new ArrayList<KeyValuePair>();
		List<KeyValuePair> invalidFileNameKVPs = new ArrayList<KeyValuePair>();

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

		writeJSON(actionRequest, actionResponse, jsonArray);
	}

	protected void addMultipleFileEntries(
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse, String selectedFileName,
			List<KeyValuePair> validFileNameKVPs,
			List<KeyValuePair> invalidFileNameKVPs)
		throws Exception {

		String originalSelectedFileName = selectedFileName;

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long repositoryId = ParamUtil.getLong(actionRequest, "repositoryId");
		long folderId = ParamUtil.getLong(actionRequest, "folderId");
		String description = ParamUtil.getString(actionRequest, "description");
		String changeLog = ParamUtil.getString(actionRequest, "changeLog");

		FileEntry tempFileEntry = null;

		try {
			tempFileEntry = TempFileUtil.getTempFile(
				themeDisplay.getScopeGroupId(), themeDisplay.getUserId(),
				selectedFileName, _TEMP_FOLDER_NAME);

			String mimeType = tempFileEntry.getMimeType();

			String extension = FileUtil.getExtension(selectedFileName);

			int pos = selectedFileName.lastIndexOf(TEMP_RANDOM_SUFFIX);

			if (pos != -1) {
				selectedFileName = selectedFileName.substring(0, pos);

				if (Validator.isNotNull(extension)) {
					selectedFileName =
						selectedFileName + StringPool.PERIOD + extension;
				}
			}

			while (true) {
				try {
					DLAppLocalServiceUtil.getFileEntry(
						themeDisplay.getScopeGroupId(), folderId,
						selectedFileName);

					StringBundler sb = new StringBundler(5);

					sb.append(FileUtil.stripExtension(selectedFileName));
					sb.append(StringPool.DASH);
					sb.append(StringUtil.randomString());

					if (Validator.isNotNull(extension)) {
						sb.append(StringPool.PERIOD);
						sb.append(extension);
					}

					selectedFileName = sb.toString();
				}
				catch (Exception e) {
					break;
				}
			}

			InputStream inputStream = tempFileEntry.getContentStream();
			long size = tempFileEntry.getSize();

			ServiceContext serviceContext = ServiceContextFactory.getInstance(
				DLFileEntry.class.getName(), actionRequest);

			FileEntry fileEntry = DLAppServiceUtil.addFileEntry(
				repositoryId, folderId, selectedFileName, mimeType,
				selectedFileName, description, changeLog, inputStream, size,
				serviceContext);

			AssetPublisherUtil.addAndStoreSelection(
				actionRequest, DLFileEntry.class.getName(),
				fileEntry.getFileEntryId(), -1);

			AssetPublisherUtil.addRecentFolderId(
				actionRequest, DLFileEntry.class.getName(), folderId);

			validFileNameKVPs.add(
				new KeyValuePair(selectedFileName, originalSelectedFileName));

			return;
		}
		catch (Exception e) {
			String errorMessage = getAddMultipleFileEntriesErrorMessage(
				portletConfig, actionRequest, actionResponse, e);

			invalidFileNameKVPs.add(
				new KeyValuePair(originalSelectedFileName, errorMessage));
		}
		finally {
			if (tempFileEntry != null) {
				TempFileUtil.deleteTempFile(tempFileEntry.getFileEntryId());
			}
		}
	}

	protected void addTempFileEntry(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		UploadPortletRequest uploadPortletRequest =
			PortalUtil.getUploadPortletRequest(actionRequest);

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long folderId = ParamUtil.getLong(uploadPortletRequest, "folderId");
		String sourceFileName = uploadPortletRequest.getFileName("file");

		String title = sourceFileName;

		StringBundler sb = new StringBundler(5);

		sb.append(FileUtil.stripExtension(sourceFileName));
		sb.append(TEMP_RANDOM_SUFFIX);
		sb.append(StringUtil.randomString());

		String extension = FileUtil.getExtension(sourceFileName);

		if (Validator.isNotNull(extension)) {
			sb.append(StringPool.PERIOD);
			sb.append(extension);
		}

		sourceFileName = sb.toString();

		InputStream inputStream = null;

		try {
			inputStream = uploadPortletRequest.getFileAsStream("file");

			String contentType = uploadPortletRequest.getContentType("file");

			DLAppServiceUtil.addTempFileEntry(
				themeDisplay.getScopeGroupId(), folderId, sourceFileName,
				_TEMP_FOLDER_NAME, inputStream, contentType);

			JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

			jsonObject.put("name", sourceFileName);
			jsonObject.put("title", title);

			writeJSON(actionRequest, actionResponse, jsonObject);
		}
		catch (Exception e) {
			UploadException uploadException =
				(UploadException)actionRequest.getAttribute(
					WebKeys.UPLOAD_EXCEPTION);

			if ((uploadException != null) &&
				(uploadException.getCause() instanceof IOFileUploadException)) {

				// Cancelled a temporary upload

			}
			else if ((uploadException != null) &&
					 uploadException.isExceededSizeLimit()) {

				throw new FileSizeException(uploadException.getCause());
			}
			else {
				throw e;
			}
		}
		finally {
			StreamUtil.cleanUp(inputStream);
		}
	}

	protected void handleUploadException(
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse, String cmd, Exception e)
		throws Exception {

		if (e instanceof AssetCategoryException ||
			e instanceof AssetTagException) {

			SessionErrors.add(actionRequest, e.getClass(), e);
		}
		else if (e instanceof DuplicateFileException ||
				 e instanceof DuplicateFolderNameException ||
				 e instanceof LiferayFileItemException ||
				 e instanceof FileExtensionException ||
				 e instanceof FileMimeTypeException ||
				 e instanceof FileNameException ||
				 e instanceof FileSizeException ||
				 e instanceof NoSuchFolderException ||
				 e instanceof SourceFileNameException ||
				 e instanceof StorageFieldRequiredException ||
				 e instanceof QuotaExceededException) {

			if (Validator.isNull(cmd)) {
				UploadException uploadException =
					(UploadException)actionRequest.getAttribute(
						WebKeys.UPLOAD_EXCEPTION);

				if (uploadException != null) {
					String uploadExceptionRedirect = ParamUtil.getString(
						actionRequest, "uploadExceptionRedirect");

					actionResponse.sendRedirect(uploadExceptionRedirect);
				}

				SessionErrors.add(actionRequest, e.getClass());

				return;
			}
			else if (!cmd.equals(Constants.ADD_DYNAMIC) &&
					 !cmd.equals(Constants.ADD_MULTIPLE) &&
					 !cmd.equals(Constants.ADD_TEMP)) {

				if (e instanceof QuotaExceededException) {
					SessionErrors.add(actionRequest, "quotaExceededError");
				} else {
					SessionErrors.add(actionRequest, e.getClass());
				}

				return;
			}

			if (e instanceof DuplicateFileException ||
				e instanceof FileExtensionException ||
				e instanceof FileNameException ||
				e instanceof FileSizeException ||
				e instanceof QuotaExceededException) {

				HttpServletResponse response =
					PortalUtil.getHttpServletResponse(actionResponse);

				response.setContentType(ContentTypes.TEXT_HTML);
				response.setStatus(HttpServletResponse.SC_OK);

				String errorMessage = StringPool.BLANK;
				int errorType = 0;

				ThemeDisplay themeDisplay =
					(ThemeDisplay)actionRequest.getAttribute(
						WebKeys.THEME_DISPLAY);

				if (e instanceof DuplicateFileException) {
					errorMessage = themeDisplay.translate(
						"please-enter-a-unique-document-name");
					errorType =
						ServletResponseConstants.SC_DUPLICATE_FILE_EXCEPTION;
				}
				else if (e instanceof FileExtensionException) {
					errorMessage = themeDisplay.translate(
						"please-enter-a-file-with-a-valid-extension-x",
						StringUtil.merge(
							getAllowedFileExtensions(
								portletConfig, actionRequest, actionResponse)));
					errorType =
						ServletResponseConstants.SC_FILE_EXTENSION_EXCEPTION;
				}
				else if (e instanceof FileNameException) {
					errorMessage = themeDisplay.translate(
						"please-enter-a-file-with-a-valid-file-name");
					errorType = ServletResponseConstants.SC_FILE_NAME_EXCEPTION;
				}
				else if (e instanceof FileSizeException) {
					long fileMaxSize = PrefsPropsUtil.getLong(
							PropsKeys.DL_FILE_MAX_SIZE);

					if (fileMaxSize == 0) {
						fileMaxSize = PrefsPropsUtil.getLong(
							PropsKeys.UPLOAD_SERVLET_REQUEST_IMPL_MAX_SIZE);
					}

					fileMaxSize /= 1024;

					errorMessage = themeDisplay.translate(
						"please-enter-a-file-with-a-valid-file-size-no-larger" +
							"-than-x",
						fileMaxSize);

					errorType = ServletResponseConstants.SC_FILE_SIZE_EXCEPTION;
				}
				else if (e instanceof QuotaExceededException) {
					errorMessage = QuotaUtil.getResource(actionRequest,
						"quota-exceeded-error");

					errorType = 494;
				}

				JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

				jsonObject.put("message", errorMessage);
				jsonObject.put("status", errorType);

				writeJSON(actionRequest, actionResponse, jsonObject);
			}

			SessionErrors.add(actionRequest, e.getClass());
		}
		else if (e instanceof DuplicateLockException ||
				 e instanceof InvalidFileVersionException ||
				 e instanceof NoSuchFileEntryException ||
				 e instanceof PrincipalException) {

			if (e instanceof DuplicateLockException) {
				DuplicateLockException dle = (DuplicateLockException)e;

				SessionErrors.add(actionRequest, dle.getClass(), dle.getLock());
			}
			else {
				SessionErrors.add(actionRequest, e.getClass());
			}

			setForward(actionRequest, "portlet.document_library.error");
		}
		else {
			throw e;
		}
	}

	private static final String _TEMP_FOLDER_NAME ="com.liferay.portlet.documentlibrary.action"+
			".EditFileEntryAction";
}
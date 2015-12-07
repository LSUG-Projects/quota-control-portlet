create index IX_9D84DA90 on LSUGQUOTA_Quota (classNameId);
create index IX_44F8DB53 on LSUGQUOTA_Quota (classNameId, classPK);
create index IX_5D293749 on LSUGQUOTA_Quota (classPK, classNameId);
create index IX_53C717A4 on LSUGQUOTA_Quota (companyId, classNameId);
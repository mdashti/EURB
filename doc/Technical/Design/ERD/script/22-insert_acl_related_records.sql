-- INSERTING SID for current users
INSERT INTO ACL_SID (sid , principal) SELECT USERNAME,1 AS PRINCIPAL FROM USERS  WHERE USERNAME NOT IN (SELECT SID FROM ACL_SID WHERE PRINCIPAL=1);
-- INSERTING SID for current groupss
INSERT INTO ACL_SID (sid , principal) SELECT ID,      0 AS PRINCIPAL FROM GROUPS WHERE ID       NOT IN (SELECT SID FROM ACL_SID WHERE PRINCIPAL=0);
-- INSERTING CLASS for category and report
INSERT INTO ACL_CLASS (ID, CLASS ) VALUES
(1, 'com.sharifpro.eurb.builder.model.ReportCategory'),
(2, 'com.sharifpro.eurb.builder.model.ReportDesign');
-- INSERING IDENTITY for current categories
INSERT INTO acl_object_identity ( object_id_class, object_id_identity, parent_object, owner_sid, entries_inheriting )
SELECT DISTINCT 1,o.id,null,sid.id,1 FROM report_category o, persistable_object p, acl_sid sid WHERE o.id=p.id AND sid.principal=1 AND sid.sid=p.creator AND o.id NOT IN (SELECT object_id_identity FROM acl_object_identity );
-- INSERING IDENTITY for current reports
INSERT INTO acl_object_identity ( object_id_class, object_id_identity, parent_object, owner_sid, entries_inheriting )
SELECT DISTINCT 2,o.id,null,sid.id,1 FROM report_design o, persistable_object p, acl_sid sid WHERE o.id=p.id AND sid.principal=1 AND sid.sid=p.creator AND o.is_current = 1 AND o.record_status <> 'D' AND o.id NOT IN (SELECT object_id_identity FROM acl_object_identity );
-- UPDATING PARENT-CHILD relationship for current categories
UPDATE acl_object_identity childIden, acl_object_identity parentIden, report_category parentCat, report_category childCat
SET childIden.parent_object = parentIden.id
WHERE parentCat.id = parentIden.object_id_identity
AND childCat.id = childIden.object_id_identity
AND childCat.parent_category_id = parentCat.id
AND childIden.object_id_class = 1
AND parentIden.object_id_class = 1;
-- UPDATING PARENT-CHILD relationship for current reports
UPDATE acl_object_identity childIden, acl_object_identity parentIden, report_category parentCat, report_design childRpt
SET childIden.parent_object = parentIden.id
WHERE parentCat.id = parentIden.object_id_identity
AND childRpt.id = childIden.object_id_identity
AND childRpt.category_id = parentCat.id
AND childIden.object_id_class = 2
AND parentIden.object_id_class = 1;
-- INSERTING ACL ENTRIES for current categories
INSERT INTO acl_entry ( acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure )
SELECT DISTINCT i.id,1 as ace_order,i.owner_sid,63 as mask, 1 as granting, 1 as audit_success, 1 as audit_failure FROM report_category o, persistable_object p, acl_sid sid, acl_object_identity i WHERE o.id=p.id AND sid.principal=1 AND sid.sid=p.creator AND i.object_id_identity=o.id AND i.object_id_class = 1 AND i.id NOT IN (SELECT acl_object_identity FROM acl_entry );
-- INSERTING ACL ENTRIES for current reports
INSERT INTO acl_entry ( acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure )
SELECT DISTINCT i.id,1 as ace_order,i.owner_sid,63 as mask, 1 as granting, 1 as audit_success, 1 as audit_failure FROM report_design o, persistable_object p, acl_sid sid, acl_object_identity i WHERE o.id=p.id AND sid.principal=1 AND sid.sid=p.creator AND o.is_current = 1 AND o.record_status <> 'D' AND i.object_id_identity=o.id AND i.object_id_class = 2 AND i.id NOT IN (SELECT acl_object_identity FROM acl_entry );

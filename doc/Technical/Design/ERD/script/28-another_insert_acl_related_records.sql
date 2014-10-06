-- INSERTING CLASS for db_config and table_mapping
INSERT INTO ACL_CLASS (ID, CLASS ) VALUES
(3, 'com.sharifpro.eurb.management.mapping.model.DbConfig'),
(4, 'com.sharifpro.eurb.management.mapping.model.TableMapping');
-- INSERING IDENTITY for current db_config
INSERT INTO acl_object_identity ( object_id_class, object_id_identity, parent_object, owner_sid, entries_inheriting )
SELECT DISTINCT 3,o.id,null,sid.id,1 FROM db_config o, persistable_object p, acl_sid sid WHERE o.id=p.id AND sid.principal=1 AND sid.sid=p.creator AND o.id NOT IN (SELECT object_id_identity FROM acl_object_identity );
-- INSERING IDENTITY for current table_mapping
INSERT INTO acl_object_identity ( object_id_class, object_id_identity, parent_object, owner_sid, entries_inheriting )
SELECT DISTINCT 4,o.id,null,sid.id,1 FROM table_mapping o, persistable_object p, acl_sid sid WHERE o.id=p.id AND sid.principal=1 AND sid.sid=p.creator AND o.id NOT IN (SELECT object_id_identity FROM acl_object_identity );
-- UPDATING PARENT-CHILD relationship for current db_configs and table_mappings
UPDATE acl_object_identity childIden, acl_object_identity parentIden, db_config parentDB, table_mapping childTbl
SET childIden.parent_object = parentIden.id
WHERE parentDB.id = parentIden.object_id_identity
AND childTbl.id = childIden.object_id_identity
AND childTbl.db_config_id = parentDB.id
AND childIden.object_id_class = 4
AND parentIden.object_id_class = 3;
-- INSERTING ACL ENTRIES for current db_config
INSERT INTO acl_entry ( acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure )
SELECT DISTINCT i.id,1 as ace_order,i.owner_sid,63 as mask, 1 as granting, 1 as audit_success, 1 as audit_failure FROM db_config o, persistable_object p, acl_sid sid, acl_object_identity i WHERE o.id=p.id AND sid.principal=1 AND sid.sid=p.creator AND i.object_id_identity=o.id AND i.object_id_class = 3 AND i.id NOT IN (SELECT acl_object_identity FROM acl_entry );
-- INSERTING ACL ENTRIES for current table_mapping
INSERT INTO acl_entry ( acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure )
SELECT DISTINCT i.id,1 as ace_order,i.owner_sid,63 as mask, 1 as granting, 1 as audit_success, 1 as audit_failure FROM table_mapping o, persistable_object p, acl_sid sid, acl_object_identity i WHERE o.id=p.id AND sid.principal=1 AND sid.sid=p.creator AND i.object_id_identity=o.id AND i.object_id_class = 4 AND i.id NOT IN (SELECT acl_object_identity FROM acl_entry );

INSERT INTO naming_context (context_id, dn, parent) VALUES(1, 'dc=objectledge,dc=org', -1);

INSERT INTO naming_context (context_id, dn, parent) VALUES(2, 'ou=people,dc=objectledge,dc=org', 1);

-- omi� zaj�te idki
INSERT INTO id_table (table, next_id) VALUES('naming_context', 3);


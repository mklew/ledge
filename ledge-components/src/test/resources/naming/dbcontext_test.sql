-- Dane wymagane przez DbContextTest

-- przyk�adowy root do test�w - tak jak w LDAPie
INSERT INTO db_context (db_context_id, dn, parent) VALUES(1, 'o=testcompany,c=pl', 0);

-- przyk�adowy root do test�w - properties
INSERT INTO db_context (db_context_id, dn, parent) VALUES(2, 'config.component', 0);


INSERT INTO db_context (db_context_id, dn, parent) VALUES(3, 'dc=objectledge,dc=org', 0);

INSERT INTO db_context (db_context_id, dn, parent) VALUES(4, 'ou=people,dc=objectledge,dc=org', 3);

-- omi� zaj�te idki
INSERT INTO id_table (table, next_id) VALUES('db_context', 6);


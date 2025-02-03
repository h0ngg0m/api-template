INSERT INTO admin (ID, NAME, LOGIN_ID, PASSWORD, ROLE, CREATED_AT, UPDATED_AT)
VALUES (1, 'admin', 'admin', '$2a$10$7eVxlG3WEZqGksKO/u3NC.9VZyVbacL9GO73LVi1k06uqiX8qx4sy', 'SUPER', now(), now())
     , (2, 'admin2', 'admin2', '$2a$10$7eVxlG3WEZqGksKO/u3NC.9VZyVbacL9GO73LVi1k06uqiX8qx4sy', 'SUPER', now(), now())
     , (3, 'admin3', 'admin3', '$2a$10$7eVxlG3WEZqGksKO/u3NC.9VZyVbacL9GO73LVi1k06uqiX8qx4sy', 'SUPER', now(), now())
     , (4, 'admin4', 'admin4', '$2a$10$7eVxlG3WEZqGksKO/u3NC.9VZyVbacL9GO73LVi1k06uqiX8qx4sy', 'SUPER', now(), now())
     , (5, 'admin5', 'admin5', '$2a$10$7eVxlG3WEZqGksKO/u3NC.9VZyVbacL9GO73LVi1k06uqiX8qx4sy', 'SUPER', now(), now())
     , (6, 'admin6', 'admin6', '$2a$10$7eVxlG3WEZqGksKO/u3NC.9VZyVbacL9GO73LVi1k06uqiX8qx4sy', 'SUPER', now(), now())
     , (7, 'admin7', 'admin7', '$2a$10$7eVxlG3WEZqGksKO/u3NC.9VZyVbacL9GO73LVi1k06uqiX8qx4sy', 'SUPER', now(), now())
     , (8, 'admin8', 'admin8', '$2a$10$7eVxlG3WEZqGksKO/u3NC.9VZyVbacL9GO73LVi1k06uqiX8qx4sy', 'SUPER', now(), now());

INSERT INTO notice (ID, TITLE, CONTENT, CREATED_AT, UPDATED_AT)
VALUES (1, '공지사항1', '공지사항1 내용', now(), now())
     , (2, '공지사항2', '공지사항2 내용', now(), now())
     , (3, '공지사항3', '공지사항3 내용', now(), now())
     , (4, '공지사항4', '공지사항4 내용', now(), now())
     , (5, '공지사항5', '공지사항5 내용', now(), now())
     , (6, '공지사항6', '공지사항6 내용', now(), now())
     , (7, '공지사항7', '공지사항7 내용', now(), now())
     , (8, '공지사항8', '공지사항8 내용', now(), now());

INSERT INTO code_group (ID, TITLE, CREATED_AT, UPDATED_AT)
VALUES (1, '코드그룹1', now(), now())
     , (2, '코드그룹2', now(), now())
     , (3, '코드그룹3', now(), now());

INSERT INTO code (ID, TITLE, VALUE, CREATED_AT, UPDATED_AT, CODE_GROUP_ID)
VALUES (1, '코드1-1', 'CODE1-1', now(), now(), 1)
     , (2, '코드1-2', 'CODE1-2', now(), now(), 1)
     , (3, '코드1-3', 'CODE1-3', now(), now(), 1)
     , (4, '코드2-1', 'CODE2-1', now(), now(), 2)
     , (5, '코드2-2', 'CODE2-2', now(), now(), 2)
     , (6, '코드3-1', 'CODE3-1', now(), now(), 3);
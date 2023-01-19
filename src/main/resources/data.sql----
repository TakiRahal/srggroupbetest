
-- -- Add all authorities

INSERT INTO sg_authority (id,name)
SELECT '1','ROLE_MODERATOR'
WHERE NOT EXISTS
    (SELECT *
     FROM sg_authority
     WHERE id = '1' AND name='ROLE_MODERATOR');

INSERT INTO sg_authority (id,name)
SELECT '2','ROLE_ADMIN'
WHERE NOT EXISTS
    (SELECT *
     FROM sg_authority
     WHERE id = '2' AND name='ROLE_ADMIN');


INSERT INTO sg_authority (id,name)
SELECT '3','ROLE_USER'
WHERE NOT EXISTS
    (SELECT *
     FROM sg_authority
     WHERE id = '3' AND name='ROLE_USER');


-- Add super admin user

INSERT INTO sg_user (id,first_name,last_name,username,email,activated_account,image_url,activation_key,reset_key,phone,source_connected_device,password,lang_key,link_profile_facebook,blocked)
SELECT '1','Srf','Group','srfgroup.contact@gmail.com','srfgroup.contact@gmail.com','true','image','123456789','123456789','0021624158860','WebBrowser','$2a$10$4Ba5qhmFQ14vhIwrYXNDA.Wvs/3zAkwt.u19Ceqg9hHyTn/1SOBri','en','https://www.facebook.com/profile.php?id=100054409273167', ''
WHERE NOT EXISTS
    (SELECT email
     FROM sg_user
     WHERE email = 'srfgroup.contact@gmail.com');



-- -- Add user_authority

INSERT INTO user_authorities (user_id,authority_id)
SELECT '1','1'
WHERE NOT EXISTS
    (SELECT *
     FROM user_authorities
     WHERE user_id = '1' AND authority_id='1');

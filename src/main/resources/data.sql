TRUNCATE TABLE users RESTART IDENTITY CASCADE;

INSERT INTO users (firstname, lastname, username, email, password, is_admin, is_verified, avatar)
VALUES (
    'Super', 
    'Admin', 
    'AdminBoss', 
    'admin@blog.com', 
    '$2a$10$W/c2wJFLaPhXnyQ5ux0ujeF1JdLMsefzHwJgXYwmUQpiNDdwgslgy', 
    true, 
    true, 
    'https://cdn-icons-png.flaticon.com/512/149/149071.png'
)
ON CONFLICT (email) DO NOTHING; 


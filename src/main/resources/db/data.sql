INSERT INTO public.age_limits (name, status, insert_date, update_date, description, start_age)
VALUES ('18+', 'ACTIVE', '2022-01-06 15:37:38.217319 +04:00', null, 'Для зрителей старше 18 лет', 18);
INSERT INTO public.age_limits (name, status, insert_date, update_date, description, start_age)
VALUES ('16+', 'ACTIVE', '2022-01-06 15:37:38.217319 +04:00', null, 'Для зрителей старше 16 лет', 16);
INSERT INTO public.age_limits (name, status, insert_date, update_date, description, start_age)
VALUES ('12+', 'ACTIVE', '2022-01-06 15:37:38.217319 +04:00', null, 'Для зрителей старше 12 лет', 12);
INSERT INTO public.age_limits (name, status, insert_date, update_date, description, start_age)
VALUES ('6+', 'ACTIVE', '2022-01-06 15:37:38.217319 +04:00', null, 'Для зрителей старше 6 лет', 6);
INSERT INTO public.age_limits (name, status, insert_date, update_date, description, start_age)
VALUES ('0+', 'ACTIVE', '2022-01-06 15:37:38.217319 +04:00', null, 'Без возврастных ограничений', 0);
INSERT INTO public.users (status, update_date, hash_password, user_status, username, first_name, last_name, patronymic, gender, birth_date)
VALUES ('ACTIVE', null, '$2a$10$RmQ0Z20v9KvcZYKmn5Ebiu1sQhnAvwj5gH/rP9tBQ6kPklG/mGxhq', 'ACTIVE', '7987654321','Admin', 'Admin', null, 'Male', current_date);
INSERT INTO public.user_roles (status, update_date, role, user_id)
VALUES ('ACTIVE', current_date, 'ADMIN', 1);
insert into age_limits (id, disable_date, status, update_date, description, name, start_age)
values ('0+',null,'ENABLE', null, 'Без ограничений', '0+', 0),
       ('6+',null,'ENABLE', null, 'Для зрителей старше 6 лет', '6+', 6),
       ('12+',null,'ENABLE', null, 'Для зрителей старше 12 лет', '12+', 12),
       ('16+',null,'ENABLE', null, 'Для зрителей старше 16 лет', '16+', 16),
       ('18+',null,'ENABLE', null, 'Только для взрослых', '18+', 18);

insert into countries (code, disable_date, status, update_date, full_name, short_name)
values ('us', null, 'ENABLE', null, 'Соединенные Штаты Америки', 'США'),
       ('ru', null, 'ENABLE', null, 'Российская Федерация', 'РФ');

insert into users (disable_date, status, update_date, hash_password, user_status, username, first_name, last_name, patronymic, gender)
values (null, 'ENABLE', null, '$2a$10$HBFt4LfyZQu34YzZoEsAkuD00OpEredquQ.9LTyN4hcgpPp/Ziyzq', 'ACTIVE', 'admin', 'Admin', 'Adminov', 'Adminich', 'Admin');

insert into user_roles (disable_date, status, update_date, role, user_id)
values (null, 'ENABLE', null, 'ADMIN', 3);
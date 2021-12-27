insert into age_limits (id, disable_date, status, update_date, description, name, start_age)
values ('0+', null, 'ENABLE', null, 'Без ограничений', '0+', 0),
       ('6+', null, 'ENABLE', null, 'Для зрителей старше 6 лет', '6+', 6),
       ('12+', null, 'ENABLE', null, 'Для зрителей старше 12 лет', '12+', 12),
       ('16+', null, 'ENABLE', null, 'Для зрителей старше 16 лет', '16+', 16),
       ('18+', null, 'ENABLE', null, 'Только для взрослых', '18+', 18);

insert into countries (code, disable_date, status, update_date, full_name, short_name)
values ('us', null, 'ENABLE', null, 'Соединенные Штаты Америки', 'США'),
       ('ru', null, 'ENABLE', null, 'Российская Федерация', 'РФ');

insert into halls (disable_date, status, update_date, name)
values (null, 'ENABLE', null, 'Зал 1'),
       (null, 'ENABLE', null, 'Зал 2'),
       (null, 'ENABLE', null, 'Зал 3');

DO
$do$
    BEGIN
        FOR i IN 1..10
            LOOP
                for j in 1..15
                    loop
                        insert into seats (disable_date, status, update_date, number, row, hall_id)
                        values (null, 'ENABLE', null, j, i, 1);
                    end loop;
            end loop;
    END
$do$;

insert into seats (disable_date, status, update_date, number, row, hall_id)
values (null, 'ENABLE', null, '1', '2', 1)
insert into employee(id, name, remaining_balance, total_balance) 
values 
('aa5d6b24-072c-441f-8dc8-75263da994f4', 'Moustafa', 5, 10), 
('5805b681-78b2-40be-a988-2aa4dc4e10e1','Ahmed', 10, 20);


insert into history(id, start_date, end_date) 
values 
('d269bb9b-ed72-4ecf-95f6-bf3720169746', '2020-10-20','2020-10-25'), 
('9c242609-ca90-4cc2-9d5d-347e5b0cbcb3', '2020-9-20','2020-9-25'), 
('f6d9460e-7bfb-4659-bdde-ce9d54ebbd72', '2020-8-20','2020-8-25'), 
('9eac9d20-76ca-4b0e-bb03-f1c27e28cd2d', '2020-7-20','2020-7-25'); 

insert into employee_vacation_history(employee_id, vacation_history_id) 
values 
('aa5d6b24-072c-441f-8dc8-75263da994f4', 'd269bb9b-ed72-4ecf-95f6-bf3720169746'), 
('aa5d6b24-072c-441f-8dc8-75263da994f4', '9c242609-ca90-4cc2-9d5d-347e5b0cbcb3'), 
('5805b681-78b2-40be-a988-2aa4dc4e10e1', 'f6d9460e-7bfb-4659-bdde-ce9d54ebbd72'), 
('5805b681-78b2-40be-a988-2aa4dc4e10e1', '9eac9d20-76ca-4b0e-bb03-f1c27e28cd2d'); 



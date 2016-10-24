INSERT INTO public.rol(
            id, descripcion, estado)
    VALUES ('ADMINISTRADOR', 'Administrador del Sistema', 'ACT');
INSERT INTO public.rol(
            id, descripcion, estado)
    VALUES ('PROFESOR', 'Profesor', 'ACT');
    INSERT INTO public.rol(
            id, descripcion, estado)
    VALUES ('ALUMNO', 'Alumno', 'ACT');
INSERT INTO public.usuario(
            identificacion, apellido, direccion, email, estado, nombre, password)
    VALUES ('1718891052', 'LOPEZ', '12 de octubre', 'calopezf@gmail.com', 'ACT', 'CRISTIAN', 'oldhouse');
INSERT INTO public.usuario_rol(
            rol_id, usuario_identificacion)
    VALUES ('ADMINISTRADOR','1718891052');
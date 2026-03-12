-- =========================
-- DOCTOR DATA
-- =========================

INSERT INTO doctors (name, email, phone_number, specialization, experience, created_at)
VALUES ('Dr. John Smith', 'john.smith@hospital.com', '+923001112233', 'Cardiology', 10, NOW());

INSERT INTO doctors (name, email, phone_number, specialization, experience, created_at)
VALUES ('Dr. Sarah Khan', 'sarah.khan@hospital.com', '+923004445566', 'Dermatology', 7, NOW());

INSERT INTO doctors (name, email, phone_number, specialization, experience, created_at)
VALUES ('Dr. Ali Ahmed', 'ali.ahmed@hospital.com', '+923007778899', 'Orthopedics', 12, NOW());


-- =========================
-- PATIENT DATA
-- =========================

INSERT INTO patients (name, blood_group_type, date_of_birth, gender, patient_type, email, phone_number, address, created_at)
VALUES ('Ahmad Raza', 'O_POSITIVE', '1995-05-10', 'MALE', 'OUTPATIENT', 'ahmad@example.com', '+923111234567', 'Multan', NOW());

INSERT INTO patients (name, blood_group_type, date_of_birth, gender, patient_type, email, phone_number, address, created_at)
VALUES ('Fatima Noor', 'A_POSITIVE', '1998-03-21', 'FEMALE', 'OUTPATIENT', 'fatima@example.com', '+923112223334', 'Lahore', NOW());

INSERT INTO patients (name, blood_group_type, date_of_birth, gender, patient_type, email, phone_number, address, created_at)
VALUES ('Usman Tariq', 'B_POSITIVE', '1989-11-02', 'MALE', 'INPATIENT', 'usman@example.com', '+923115556667', 'Karachi', NOW());


-- =========================
-- APPOINTMENTS
-- =========================

INSERT INTO appointments (appointment_date, appointment_time, status, patient_id, doctor_id)
VALUES ('2026-03-12', '10:00:00', 'SCHEDULED', 1, 1);

INSERT INTO appointment (appointment_date, appointment_time, status, patient_id, doctor_id)
VALUES ('2026-03-12', '11:00:00', 'SCHEDULED', 2, 2);

INSERT INTO appointment (appointment_date, appointment_time, status, patient_id, doctor_id)
VALUES ('2026-03-12', '12:00:00', 'SCHEDULED', 3, 3);

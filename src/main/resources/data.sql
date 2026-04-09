-- =========================
-- CLINIC DATA
-- =========================

INSERT INTO clinics (name, subdomain, email, phone, address, logo_url, timezone, active, plan, subscription_start_date, subscription_end_date, trial, created_at, updated_at)
VALUES ('Safwan Central Clinic', 'central', 'admin@centralclinic.com', '+923001112233', 'Main Boulevard, Karachi', null, 'Asia/Karachi', true, 'BASIC', '2026-01-01', '2026-12-31', false, NOW(), NOW());

-- =========================
-- DOCTOR DATA
-- =========================

INSERT INTO doctors (clinic_id, name, email, phone_no, specialization, experience, created_at, updated_at)
VALUES (1, 'Dr. John Smith', 'john.smith@hospital.com', '+923001112233', 'Cardiology', 10, NOW(), NOW());

INSERT INTO doctors (clinic_id, name, email, phone_no, specialization, experience, created_at, updated_at)
VALUES (1, 'Dr. Sarah Khan', 'sarah.khan@hospital.com', '+923004445566', 'Dermatology', 7, NOW(), NOW());

INSERT INTO doctors (clinic_id, name, email, phone_no, specialization, experience, created_at, updated_at)
VALUES (1, 'Dr. Ali Ahmed', 'ali.ahmed@hospital.com', '+923007778899', 'Orthopedics', 12, NOW(), NOW());


-- =========================
-- PATIENT DATA
-- =========================

INSERT INTO patients (clinic_id, name, blood_group_type, date_of_birth, gender, patient_type, email, phone_number, address, deleted, created_at, updated_at)
VALUES (1, 'Ahmad Raza', 'O_POSITIVE', '1995-05-10', 'MALE', 'OUTPATIENT', 'ahmad@example.com', '+923111234567', 'Multan', false, NOW(), NOW());

INSERT INTO patients (clinic_id, name, blood_group_type, date_of_birth, gender, patient_type, email, phone_number, address, deleted, created_at, updated_at)
VALUES (1, 'Fatima Noor', 'A_POSITIVE', '1998-03-21', 'FEMALE', 'OUTPATIENT', 'fatima@example.com', '+923112223334', 'Lahore', false, NOW(), NOW());

INSERT INTO patients (clinic_id, name, blood_group_type, date_of_birth, gender, patient_type, email, phone_number, address, deleted, created_at, updated_at)
VALUES (1, 'Usman Tariq', 'B_POSITIVE', '1989-11-02', 'MALE', 'INPATIENT', 'usman@example.com', '+923115556667', 'Karachi', false, NOW(), NOW());


-- =========================
-- APPOINTMENTS
-- =========================

INSERT INTO appointments (clinic_id, appointment_date, appointment_time, status, patient_id, doctor_id, created_at, updated_at)
VALUES (1, '2026-03-12', '10:00:00', 'SCHEDULED', 1, 1, NOW(), NOW());

INSERT INTO appointments (clinic_id, appointment_date, appointment_time, status, patient_id, doctor_id, created_at, updated_at)
VALUES (1, '2026-03-12', '11:00:00', 'SCHEDULED', 2, 2, NOW(), NOW());

INSERT INTO appointments (clinic_id, appointment_date, appointment_time, status, patient_id, doctor_id, created_at, updated_at)
VALUES (1, '2026-03-12', '12:00:00', 'SCHEDULED', 3, 3, NOW(), NOW());

-- =========================
-- PLATFORM OWNER
-- =========================

INSERT IGNORE INTO users (clinic_id, username, email, password, role, active, created_at, updated_at)
VALUES (NULL, 'platformowner', 'owner@anchorage.local', '$2a$10$zDFx3YYarlX4aJYfqt4YqerV1JxVoEHkSzrTHMPypWVz7ESlDyKMG', 'SUPER_ADMIN', true, NOW(), NOW());

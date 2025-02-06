-- Crear la tabla TASKS si no existe
CREATE TABLE IF NOT EXISTS TASKS (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    status VARCHAR(50) NOT NULL,
    create_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Initial data for tasks table
INSERT INTO tasks (id, title, description, status) 
VALUES 
(1, 'Task 1', 'Description of Task 1', 'Pending'),
(2, 'Task 2', 'Description of Task 2', 'Completed'),
(3, 'Task 3', 'Description of Task 3', 'Pending'),
(4, 'Task 4', 'Description of Task 4', 'Completed'),
(5, 'Task 5', 'Description of Task 5', 'Pending'),
(6, 'Task 6', 'Description of Task 6', 'Completed');

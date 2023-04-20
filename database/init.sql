CREATE TABLE
    users (
        user_id INT PRIMARY KEY AUTO_INCREMENT,
        username VARCHAR(50) UNIQUE NOT NULL,
        password VARCHAR(50) NOT NULL,
        email VARCHAR(50) NOT NULL,
        phone VARCHAR(50) NOT NULL,
        address VARCHAR(50) NOT NULL
    );

INSERT INTO
    users (
        username,
        password,
        email,
        phone,
        address
    )
VALUES (
        'john.doe',
        'password123',
        'john.doe@example.com',
        '+1 (555) 123-4567',
        '123 Main St'
    ), (
        'jane.doe',
        'password456',
        'jane.doe@example.com',
        '+1 (555) 987-6543',
        '456 Park Ave'
    ), (
        'bob.smith',
        'password789',
        'bob.smith@example.com',
        '+1 (555) 555-5555',
        '789 Elm St'
    );
CREATE TABLE items (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    description TEXT
);

CREATE TABLE item_images (
    id SERIAL PRIMARY KEY,
    item_id INT NOT NULL,
    image_url VARCHAR(255) NOT NULL,
    alt_text VARCHAR(255),
    FOREIGN KEY (item_id) REFERENCES items (id) ON DELETE CASCADE
);

CREATE TABLE colors (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL,
    image VARCHAR(255)
);

CREATE TABLE item_colors (
    item_id INT NOT NULL,
    color_id INT NOT NULL,
    PRIMARY KEY (item_id, color_id),
    FOREIGN KEY (item_id) REFERENCES items (id) ON DELETE CASCADE,
    FOREIGN KEY (color_id) REFERENCES colors (id) ON DELETE CASCADE
);

CREATE Table clients (
    id SERIAL PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL,
    is_active BOOLEAN DEFAULT TRUE
);

CREATE Table admin_user (
    id SERIAL PRIMARY KEY,
    username VARCHAR(100) UNIQUE not NULL,
    password_hash VARCHAR(255) not NULL
)
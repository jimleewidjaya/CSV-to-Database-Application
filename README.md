# CSV to Database Application

### Sebuah aplikasi (API) untuk menghandle import data dari CSV ke Database dengan spesifikasi:
#### - Tidak blocking dari sisi user (user masih bisa melakukan hal lain selama menunggu hasil)
#### - File bisa disimpan sebagai future reference
#### - Database Table name: Users
#### - Column: username, password, name
#### - CSV Column Names and the order uploaded must be the same as the Column names above!

## API

## 1. Insert User From CSV
### Endpoint: POST /api/files

````json
{
    "file": "file"
}
````

### Response Body (Success)

````json
{
    "data": "OK"
}
````

### Response Body (Username (Primary Key) already exist)

````json
{
    "errors": "Username already registered!"
}
````

### Response Body (Extension of the file uploaded is not .csv)

````json
{
    "errors": "Extension of the file uploaded must be .csv!"
}
````

### Response Body (CSV Column Names and the order uploaded is not match with the requirements)

````json
{
    "errors": "CSV Format doesn't Match with The Requirements!"
}
````

## 2. Register User
### Endpoint: POST /api/users

````json
{
    "username": "jimleecw",
    "password": "rahasia",
    "name": "Jimlee"
}
````

### Response Body (Success)

````json
{
    "data": "OK"
}
````

### Response Body (Failed)

````json
{
    "errors": "Username already registered!"
}
````
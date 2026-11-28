# Market Backend

## ADA 12 · Autenticación en Swagger

Con Java 25 y PostgreSQL en ejecución, configura estas variables en PowerShell desde la raíz del proyecto. Define `DB_PASSWORD` con la contraseña de PostgreSQL. Si tu base o usuario difieren de los valores predeterminados, configura también `DB_URL` y `DB_USERNAME`. Si ya tienes las credenciales en `application-dev.properties`, puedes activar ese perfil con `$env:SPRING_PROFILES_ACTIVE = 'dev'`.

```powershell
$jwtBytes = New-Object byte[] 32
$jwtRandom = [System.Security.Cryptography.RandomNumberGenerator]::Create()
$jwtRandom.GetBytes($jwtBytes)
$jwtRandom.Dispose()
$env:JWT_SECRET = [Convert]::ToBase64String($jwtBytes)
$env:AUTH_DEMO_PASSWORD = '1234'
$env:SERVER_ADDRESS = '127.0.0.1'
.\gradlew.bat bootRun
```

`JWT_SECRET` es la clave de firma HS256 y debe tener al menos 32 caracteres. Al reiniciar con una clave nueva, los tokens anteriores dejan de ser válidos. `AUTH_DEMO_PASSWORD` habilita la creación local de la cuenta `demo` si aún no existe. PostgreSQL guarda su hash BCrypt en `usuarios.password_hash`. Si la cuenta ya existe, conserva su contraseña.

1. Abre [Swagger UI](http://localhost:8090/swagger-ui/index.html) y ejecuta `GET /categories`. Debe devolver `401` sin un token.
2. En `POST /auth/login`, pulsa **Try it out** y envía `{"username":"demo","password":"1234"}`. Debe devolver `200` con un `token` válido durante una hora. Una contraseña incorrecta devuelve `401`.
3. Copia el valor de `token`, pulsa **Authorize** y pégalo tal cual. Swagger agrega el prefijo `Bearer` al encabezado `Authorization`.
4. Ejecuta nuevamente `GET /categories`. Debe devolver `200`.

Abre [el listado de productos](http://localhost:8090/products) para consultar el JSON desde el navegador. `GET /products` admite peticiones sin token.

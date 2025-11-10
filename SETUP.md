# Environment Setup Instructions

## Email Configuration

This application requires Gmail credentials for sending order confirmation emails.

### Step 1: Set Environment Variables

You have two options:

#### Option A: Using `.env` file (Recommended for Development)

1. Copy the example file:
   ```bash
   cp .env.example .env
   ```

2. Edit `.env` and add your actual credentials:
   ```bash
   EMAIL_USERNAME=your-email@gmail.com
   EMAIL_PASSWORD=your-app-password-here
   ```

3. **IMPORTANT:** The `.env` file is already in `.gitignore` and will NOT be committed to git.

#### Option B: Using System Environment Variables

**macOS/Linux:**
```bash
export EMAIL_USERNAME=your-email@gmail.com
export EMAIL_PASSWORD=your-app-password-here
```

Add these to your `~/.zshrc` or `~/.bashrc` to make them permanent.

**Windows (PowerShell):**
```powershell
$env:EMAIL_USERNAME="your-email@gmail.com"
$env:EMAIL_PASSWORD="your-app-password-here"
```

**Windows (Command Prompt):**
```cmd
set EMAIL_USERNAME=your-email@gmail.com
set EMAIL_PASSWORD=your-app-password-here
```

### Step 2: Generate Gmail App Password

1. Go to your Google Account: https://myaccount.google.com/
2. Navigate to **Security** > **2-Step Verification** (enable if not already)
3. Scroll down to **App passwords**
4. Select **Mail** and **Other (Custom name)**
5. Enter "Restaurant API" as the name
6. Click **Generate**
7. Copy the 16-character password (format: `xxxx xxxx xxxx xxxx`)
8. Use this as your `EMAIL_PASSWORD`

### Step 3: Run the Application

```bash
mvn spring-boot:run
```

The application will read the environment variables automatically.

### Verify Setup

If environment variables are not set, you'll see an error like:
```
Could not resolve placeholder 'EMAIL_USERNAME' in value "${EMAIL_USERNAME}"
```

If you see this, double-check that your environment variables are set correctly.

---

## MySQL Configuration (Future)

When ready to switch to MySQL:

1. Uncomment MySQL variables in `.env`:
   ```bash
   MYSQL_URL=jdbc:mysql://localhost:3306/restaurant_db?useSSL=false&serverTimezone=UTC
   MYSQL_USERNAME=root
   MYSQL_PASSWORD=your-mysql-password
   ```

2. Update `application.properties` to use these variables

3. Create the database:
   ```sql
   CREATE DATABASE restaurant_db;
   ```

---

## Security Notes

- **NEVER** commit the `.env` file to git
- **NEVER** share your Gmail app password
- The `.env.example` file is safe to commit (contains no real credentials)
- For production, use proper secrets management (AWS Secrets Manager, Azure Key Vault, etc.)

# AR Hunt

Welcome to the **AR Hunt**! This project is built with **Kotlin**, **Jetpack Compose**, **ARCore**, **SceneView**, and **Google Maps API**.

---

## Getting Started

### **Setup**
1. **Fork and Clone the Repository**  
   Start by forking the repository and cloning your forked version locally:
   ```bash
   git clone <YOUR_FORK_URL>
   cd <PROJECT_DIRECTORY>
   ```

2. **Add Remote Upstream**  
   Link the main repository to fetch updates:
   ```bash
   git remote add upstream <MAIN_REPO_URL>
   ```

3. **Open in Android Studio**  
   - Make sure you have the latest **Android Studio** installed.
   - Open the project in Android Studio and sync the Gradle files.
   - Build and run the app on your emulator or device.

---

### **Git Hooks**
This project includes custom Git hooks. Install them by running:
```bash
git config core.hooksPath .githooks
```

---

### **Working with the App**
- The app uses **ARCore** and **Google Maps API**, which require API keys to function. You will have to create your own keys.
- After obtaining the keys, you can choose either of the two options:
1. Option 1: Create a New Client on DAuth
   - Set up a client on [DAuth](https://auth.delta.nitt.edu/).  
   - Run the [backend repo](https://github.com/delta/ar-hunt-backend) locally, and connect it with the app.

2. Option 2: Bypass Authentication
   - If you don't want to set up the backend, bypass authentication by commenting out the relevant checks in the code.  

⚠️ **Important:**  
Do not commit or push any API keys or sensitive information to the repository.

---

### **Branch for Contributions**
All contributions should be raised as pull requests to the `dwoc` branch.  

#### **Steps to Contribute**
1. Check for open issues in the [Issues](https://github.com/ar-hunt-app/issues) section.  
2. Comment on the issue you'd like to work on and request it to be assigned to you.  
3. Fork the repo and push the changes to your fork.
4. Raise a PR to the `dwoc` branch.  

---

### **Prerequisites**
- **Android Studio:** Latest stable version.
- **Java Development Kit (JDK):** Version 11 or higher.
- **API Keys:** For ARCore, SceneView, and Google Maps APIs.
- **Gradle:** Ensure it is updated and compatible with the project.

---

### **Guidelines for Contributions**
- Follow the project’s **code style** and conventions.  
- Test your changes thoroughly before raising a PR.  
- Include meaningful commit messages and a clear description in your pull request.  
- Respect others’ contributions and communicate effectively in issues and PRs.  

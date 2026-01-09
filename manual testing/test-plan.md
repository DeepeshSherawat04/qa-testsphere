# Test Plan – Task Management Application

## 1. Introduction
This test plan describes the testing strategy, scope, approach, resources, and schedule
for testing the Task Management Web Application.

The goal is to ensure the application works correctly across different operating systems,
browsers, and environments, including legacy systems.

---

## 2. Objective
- Verify all functional requirements of the application
- Identify defects at early stages
- Ensure compatibility across supported OS and browsers
- Validate data integrity in the database
- Ensure application readiness for release

---

## 3. Scope of Testing

### In-Scope
- Login and Logout functionality
- Task creation, update, deletion
- Task status management
- UI validation
- Database validation
- Compatibility testing
- Regression testing

### Out-of-Scope
- Performance testing
- Security testing
- Mobile testing

---

## 4. Test Environment

| Component | Details |
|---------|--------|
| Operating Systems | Windows XP (conceptual), Windows 7, Windows 8, Windows 11 |
| Browsers | Chrome, Firefox, Edge |
| Database | PostgreSQL |
| Tools | VS Code, Git, Selenium, Jenkins |

---

## 5. Testing Types
- Manual Testing
- Functional Testing
- Regression Testing
- Compatibility Testing
- Database Testing

---

## 6. Test Design Techniques
- Equivalence Partitioning
- Boundary Value Analysis
- State Transition Testing
- Decision Table Testing

---

## 7. Entry Criteria
- Application build available
- Test environment ready
- Test cases reviewed and approved

---

## 8. Exit Criteria
- All critical and high-severity bugs fixed
- Test cases executed
- Test summary report prepared
- Stakeholder sign-off received

---

## 9. Risks and Mitigation

| Risk | Mitigation |
|----|-----------|
| OS-specific issues | Perform compatibility testing |
| Browser differences | Test on multiple browsers |
| Legacy system issues | Document known limitations |

---

## 10. Deliverables
- Test Plan
- Test Scenarios
- Test Cases
- Bug Reports
- Test Summary Report

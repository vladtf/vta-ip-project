# SPECIFICAȚIA CERINȚELOR

## CUPRINS

- [SPECIFICAȚIA CERINȚELOR](#specificația-cerințelor)
  - [CUPRINS](#cuprins)
  - [INTRODUCERE](#introducere)
    - [Scopul documentului](#scopul-documentului)
    - [Domeniul/contextul de utilizare al sistemului](#domeniulcontextul-de-utilizare-al-sistemului)
    - [Lista de definiții si abrevieri](#lista-de-definiții-si-abrevieri)
  - [DESCRIERE GENERALĂ](#descriere-generală)
    - [Scurtă descriere a sistemului](#scurtă-descriere-a-sistemului)
    - [Motivație (de ce este necesar)](#motivație-de-ce-este-necesar)
    - [Produse similare](#produse-similare)
    - [Riscurile proiectului: competiție, factori de experiență, de planificare, tehnologici, externi](#riscurile-proiectului-competiție-factori-de-experiență-de-planificare-tehnologici-externi)
  - [SISTEMUL PROPUS](#sistemul-propus)
    - [Descrierea categoriilor de utilizatori direcți/indirecți ai sistemului](#descrierea-categoriilor-de-utilizatori-direcțiindirecți-ai-sistemului)
    - [Cerințe de sistem](#cerințe-de-sistem)
    - [Cerințe funcționale](#cerințe-funcționale)
    - [Cerințe nefuncționale (curs „Extragerea cerințelor”): constrângeri hardware/software, de](#cerințe-nefuncționale-curs-extragerea-cerințelor-constrângeri-hardwaresoftware-de)
    - [Modele ale sistemului (curs „Extragerea cerințelor”)](#modele-ale-sistemului-curs-extragerea-cerințelor)
      - [Actors and use cases through which they interact](#actors-and-use-cases-through-which-they-interact)
      - [Descrierea cazurilor de utilizare ale sistemului. Pentru fiecare caz de utilizare:](#descrierea-cazurilor-de-utilizare-ale-sistemului-pentru-fiecare-caz-de-utilizare)
      - [Diagrama de contex](#diagrama-de-contex)

## INTRODUCERE

### Scopul documentului

Scopul acestui document este de a descrie cerintele sistemului de gestiune a unui cont bancar. Sistemul va fi o aplicatie web care va permite
autentificarea securizata a unui utilizator, gestionarea contului bancar, a cardurilor asociate, a tranzactiilor, a soldului, a extraselor de cont, a contului de economii, a plati cu cardul la un comerciant (fictiv).

### Domeniul/contextul de utilizare al sistemului

### Lista de definiții si abrevieri

TODO

## DESCRIERE GENERALĂ

### Scurtă descriere a sistemului

TODO

### Motivație (de ce este necesar)

TODO

### Produse similare

TODO

### Riscurile proiectului: competiție, factori de experiență, de planificare, tehnologici, externi

TODO

## SISTEMUL PROPUS

### Descrierea categoriilor de utilizatori direcți/indirecți ai sistemului

The platform includes two types of accounts: client and administrator.
Client:

- can view account information such as balances and transaction history including deposits, withdrawals, and transfers.
- can transfer funds between accounts, either within the same bank or to other banks.
- can create savings account.

Administrator:

- can troubleshoot and offer technical support, ensuring that any issues are resolved promptly and effectively.
- can create and manage user accounts for bank employees ensuring that each user has the appropriate level of access and permissions required to perform their job duties.

### Cerințe de sistem

Desktop:

- operational system Windows, Linux or Mac OS
- browser (Chrome, Firefox, Opera, Safari, Edge) that supports HTML5, CSS3, JavaScript
- internet access with at least 1 Mbps speed

Mobile:

- operational system Android or iOS
- browser (Chrome, Firefox, Opera, Safari, Edge) that supports HTML5, CSS3, JavaScript
- internet access with at least 1 Mbps speed

### Cerințe funcționale

Client is allowed to:

- login to the system using a username and password and a 2FA code
- to create a new bank account
- can create savings account
- to view the balance of the account (balances, transactions, etc.)
- to make transactions between accounts
- to pay services
- to view the account statement

Administrator can:

- manage user accounts (create, delete, update)
- manage user cards
- manage user transactions
- to solve issues raised by users

### Cerințe nefuncționale (curs „Extragerea cerințelor”): constrângeri hardware/software, de

comunicare, legislative, etc; cerințe de calitate a produsului, cerințe impuse proiectului.

1. Hardware/software constrains:

- backend application will be developed in Java, using Spring Boot framework
- frontend application will be developed in JavaScript, using React framework
- both applications will be developed using a versioning system Git
- the result of development will be two Docker images containing the respective binaries
- Docker images will be uploaded to an AWS ECR repository
- images will be deployed on an AWS ECS server cluster
- applications will run continuously

2. Security requirements:

- the backend application should not expose sensitive data (password, card number, etc.) to the users without required permissions (not even through the frontend)
- users must be authenticated before performing any action in the application
- user role must be verified before performing any action in the application
- rights of each account must be well defined and separated between privileged and unprivileged
- simple users should not be able to perform operations that require administrator rights
- for operations that require administrator rights, simple users must enter an administrator password
- any data transfer between frontend and backend must be encrypted
- backend must accept only requests from the frontend specified in the requirements (not from other frontends)
- any attempt to access unauthorized resources must be reported to the administrator

### Modele ale sistemului (curs „Extragerea cerințelor”)

#### Actors and use cases through which they interact

Client:

1. Create a new account

* the user enters the website and a registration form is displayed
* the user enters the required data (username, password, email, etc.)
* the user clicks on the register button
* an HTTP request is sent to the backend
* the backend verifies the data and creates a new bank account in the database
* the frontend displays a success message and redirects the user to the login page



2. Login

* the user enters the website and a login form is displayed
* enter the username and password and click on the login button
* an HTTP request is sent to the backend
* the backend verifies the data and returns a JWT token wich contains the user role and the user id (if the data is correct)
* frontend stores the token in the browser's local storage
* user is home page of the application where he can see his accounts

3. Create savings account
  
* the users navigates to accounts page
* click on the create savings account button
* fill the form with the required data (type of account, amount, etc.)
* click on the create button
* the frontend sends an HTTP request to the backend which creates a new savings account in the database and returns the new account to the frontend
* the user is redirected to the accounts page where he can see the new account

Prerequisites: the user must be logged in


4. View account balance

* the user navigates to balance page
* an request is sent to the backend which returns the balance of the account and the transactions
* frontend displays the balance and the transactions  

Prerequisites: the user must be logged in

5. Make transactions between accounts

* the user navigates to transactions page
* click on the make transaction button
* fill the form with the required data (type of transaction, amount, destination account, etc.)
* click on the make transaction button
* backend verifies whether the user has enough money in the account and if the destination account exists and returns the result to the frontend
* the frontend displays a success message or an error message depending on the result of the operation
* if the validation is successful, an button is displayed to confirm the transaction and the user clicks on it to confirm the transaction
* backend creates a new transaction in the database and returns the new transaction to the frontend
* user is redirected to the transactions page where he can see the new transaction

Prerequisites: the user must be logged in

6. Pay services

* the user navigates to services page
* an list of services is displayed (electricity, water, gas, etc.)
* user selects the service he wants to pay and clicks on the pay button
* the backend returns the service details and the user is redirected to the payment page
* user clicks on the pay button and an request is sent to the backend which verifies whether the user has enough money in the account and returns the result to the frontend
* if everything is ok, then user can confirm the payment and the backend creates a new transaction in the database and returns the new transaction to the frontend
* user is redirected to the transactions page where he can see the new transaction

Prerequisites: the user must be logged in

7. View account statement

* the user navigates to balance page
* press the print button and an request is sent to the backend which returns details about the account and the transactions
* an pdf file is generated and downloaded to the user's computer

Prerequisites: the user must be logged in

Administrator:

1. Manage user accounts

* the admin navigates to the users accounts page
* search for a user by username or email
* click on the edit button to edit the user's data
* an form is displayed with the user's data and the admin can edit the data and click on the save button to save the changes (also he can click on the delete button to delete the user's account)
* an confirmation message is displayed before any action is performed

Preconditions: the user must be logged in and must have administrator rights

2. Manage user cards

* the admin navigates to the users cards page
* the admin can search for a user by username or email or by card number
* click on the edit button to edit the card's data
* an form is displayed with the card's data and the admin can edit the data and click on the save button to save the changes (also he can click on the delete button to delete the card)
* an confirmation message is displayed before any action is performed

Preconditions: the user must be logged in and must have administrator rights

3. Manage user transactions

* the admin navigates to the users transactions page
* the admin can search for a user by username or email or by transaction id
* click on the edit button to edit the transaction's data
* an form is displayed with the transaction's data and the admin can edit the data and click on the save button to save the changes
* an confirmation message is displayed before any action is performed

Preconditions: the user must be logged in and must have administrator rights

1. Manage services

* the admin navigates to the services page
* he can search for a service by name or by id or by provider
* select the service he wants to edit and click on the edit button (also he can click on the delete button to delete the service or on the add button to add a new service)
* an form is displayed with the service's data where the admin can edit the service and to link the service to clients
* an confirmation message is displayed before any action is performed

Preconditions: the user must be logged in and must have administrator rights


#### Descrierea cazurilor de utilizare ale sistemului. Pentru fiecare caz de utilizare:

- actorul/ actorii implicați
- fluxul de baza: descriere și diagrama de secvența (curs „Diagrame de interacțiune”)
- alternativele la fluxul de baza
- precondiție
- postcondiție
- schița interfeței utilizator la execuția cazului de utilizare
- prioritatea la implementare (critica/ridicata/medie/scăzută) – justificare;

TODO

#### Diagrama de contex

TODO

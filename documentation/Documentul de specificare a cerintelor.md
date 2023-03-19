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
      - [Actorii si cazurile de utilizare prin care interacționează](#actorii-si-cazurile-de-utilizare-prin-care-interacționează)
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

- login to the system
- to create a new bank account
- to view the balance of the account
- to make transactions between accounts
- to pay services
- to track transactions
- to view the account statement

Administrator can:

- manage user accounts
- manage user cards
- manage user transactions
- to issues raised by users

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

TODO

#### Actorii si cazurile de utilizare prin care interacționează

TODO

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

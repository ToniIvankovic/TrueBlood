## Documentation setup

### LATEX SETUP

1. Download TexLive from [here](https://tug.org/texlive/acquire-netinstall.html)

   Choose `install-tl-windows.exe`.

2. Download TeXstudio from [here](https://www.texstudio.org/)
3. Download the Croatian dictionary for the text editor [here](https://extensions.openoffice.org/en/project/croatian-dictionary-and-hyphenation-patterns)
4. Open TexStudio, then click:

   `Options -> Configure TeXstudio -> Language checking -> Import dictionary`

   Then import the downloaded dictionary and set it as default.
5. Instructions on how to write LaTeX documentation can be found [here](https://www.fer.unizg.hr/_download/repository/LaTeX-upute.pdf)


## Frontend setup

Within the `frontend` directory, follow these steps:

1. If you don't have `yarn` installed, use the following command to install it globally:
   ```bash
   $ npm install --global yarn
   ```   
   You can find download instructions for `npm` [here](https://docs.npmjs.com/downloading-and-installing-node-js-and-npm)
2. Install required packages:
   ```bash
   $ yarn install
   ```
3. Start the development server:
   ```bash
   $ yarn start
   ```

You can also get the production version by running `yarn build`. The files will be in the `build` folder.



## Backend setup
### DOCKER

1. Download Docker from [here](https://hub.docker.com/editions/community/docker-ce-desktop-windows/)
2. Install Docker and set it up.
3. Go to root project, open terminal and type
      ```bash 
   $ docker-compose up
     ```
   Don't close that window.
4. Open another terminal in root project and type 
      ```bash 
   $ psql -h localhost -p 5430 -d Trueblood -U admin
     ```
   or use psql shell.
5. Type password "admin" (the letters will not be visible while you are typing).
6. Try out some commands (in that terminal) and make sure it works correctly.
   ### psql commands
      `\l` -> lists all databases
   
      `\d` -> lists all relations
   
      `create table ana(id int);` -> creates a table
   
      `insert into ana values (1);` -> inserts n-tuple into a table
   
      `select * from ana;` -> fetches all n-tuples from table
   
   
Play around with it! :)

7. Drop all tables so you have an empty database.
8. Run TruebloodApplication.
9. Type `\d` in terminal. There should be new tables now (5 database tables and flyway_schema_history).

Well done!

## Heroku and CI/CD

1. Create a [heroku account](https://signup.heroku.com).
1. Install [heroku CLI](https://devcenter.heroku.com/articles/heroku-cli).
2. Run `heroku login`.

The following examples use the backend application (trueblood-be), but you can also use trueblood-fe for the frontend app.

- view logs: `heroku logs --tail --app trueblood-be`
- restart app: `heroku restart --app trueblood-be`
- view builds: `heroku builds --app trueblood-be`
- connect to database: `heroku pg:psql --app trueblood-be` (this is only available for trueblood-be)

If the builds plugin is not installed, you can install it with `heroku plugins:install heroku-builds`.

You can view current pipeline status in the sidebar in CI/CD -> Pipelines, then clicking on the status of the pipeline (running/passed/failed).  
**NOTE**: In case the pipeline fails and the logs show a `Your account has reached its concurrent builds limit` error, run `heroku restart` on the application.


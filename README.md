#Documentation setup
##LATEX SETUP

1. Download TexLive from [here](https://tug.org/texlive/acquire-netinstall.html)

   Choose `install-tl-windows.exe`.

2. Download TeXstudio from [here](https://www.texstudio.org/)
3. Download the Croatian dictionary for the text editor [here](https://extensions.openoffice.org/en/project/croatian-dictionary-and-hyphenation-patterns)
4. Open TexStudio, then click:

   `Options -> Configure TeXstudio -> Language checking -> Import dictionary`

   Then import the downloaded dictionary and set it as default.
5. Instructions on how to write LaTeX documentation can be found [here](https://www.fer.unizg.hr/_download/repository/LaTeX-upute.pdf)


#Frontend setup

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



#Backend setup
##DOCKER

1. Download Docker from [here](https://hub.docker.com/editions/community/docker-ce-desktop-windows/)
2. Install Docker and set it up.
3. For running the database as a separate container use
   ```bash 
   $ docker-compose up
   ```
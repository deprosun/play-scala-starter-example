# Url-Shortner

## Running
Simply execute `sbt run`

1. Enter a URL in the text box and hit enter. You should get back a url back in the form "http://localhost:9000/_number_"
2. `number` always greater than 0.
3. Enter a different url, and you should observe a new url sent back with the "next" `number`.
4. You can copy and paste this URL back into the browser and it will take you to the original url you enter in the textbox.
5. If you enter a url that has already been entered, you will get back the same url as mentioned in 1.
6. If you click the "Url-Shortner" text on the header page, you will get directed to a home page where only text box shows.
7. If you added spaces only, upon enter-key the website gets directed to /
8. It is assumed that the code is being run on port 9000. If you choose to change the port, then the application will no

## How does the application save data

1. A file called "urls.txt" is made when you run the application.
2. This is the file that new urls get appeneded to.
3. If you want to get a new set of the db, just delete the file from the project root directory

## What did I find Challenging/Learn?

1.  Making a website using Play Framework!
2.  Sending Form to the template
3.  Play Documentation is pretty well written.
4.  Play Starter code is soo helpful
5.  For someone who hasn't written a front end code ( and that too using a completely new framework), I think did a good job :)

## Improvements to be made

1. Use a actual Database. Or something else besides a text file..
2. Check if the url entered is of a valid URL regex form
3. I'd like to study my code and see if I could make this even a "thinner" code base. Refactor/Reuse the templates. I feel I can reuse `index.scala.html` instead of creating `indexshow.scala.html`. Same with `urlshortner.scala.html` and `urlshortnershow.scala.html` templates.
4. Learn how to unit test controllers.
5. Learn more about the Play Framework 
6. Learn more about routing!
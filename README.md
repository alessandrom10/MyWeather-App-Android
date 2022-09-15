-Salve, l'applicazione è il progetto 1 "MyWeather" sviluppato per Android in Java 

-Problema geocode: come le ho scritto geocode non funziona in alcun modo, dando vari tipi di errori a seconda del momento e facendo crashare l'app. L'errore più comune risulta comunque essere deadline exceded.

    -Per sistemarlo ho provato moltissime soluzioni diverse (farlo andare in un thread/task per se, utilizzare una mia api key personalizzata, accedere tramite httpclient ecc.) ma nessuno ha mai avuto successo.
    -Ho provato anche a usare httpclient per accedera al json a cui sarebbe dovuto accedere geocode e scaricarlo in questo modo ma ho ricevuto una timeout exception
    -Ad ogni modo, nel programma il codice sia di geocode, sia della chiamata di httpclient che avrebbe dovuto "sostituirlo" sono presenti ma commentate
    -Il programma è stato quindi modificiato per arginare questo problema, il programma infatti simula la localizzazione dell'utente restituendo sempre la posizione di Parma e allo stesso modo quando gli viene richiesta un altra città restituisce sempre la città di Palermo

-L'applicazione, ad eccezione del problema con Geocode, contine tutti i requisiti richiesti e risulta completamente funzionante ed eseguibile

-L'applicazione è sviluppata secondo il pattern architetturale Model-View-Controller, con la classe LocalData che funge da Model, fornendo dati a tutto il resto dell'applicazione, mentre le varie activity (cioè i vari view controller) gestiscono le informazioni da assgnare alle view

-L'app è organizzata secondo una visione gerarchica, dove ciascuna nuova view (ad eccezione della mappa, per garantire un immagine full screen) è ingrado di ritornare alla schermata principale (cioè la root view) premendo il tasto indietro presente nell'action bar

-Ogni chiamata che potrebbe bloccare per lungo tempo l'esecuzione è stata resa asincrona, di modo da garantire un applicazione interattiva anche mentre sta caricando le previsioni del tempo o mentre sta creando i marker personalizzati nella mappa

-Infine all'interno dell'applicazione ogni elenco di dati è visualizzato tramite recyclerView per garantire un utilizzo efficente delle risorse dello smartphone


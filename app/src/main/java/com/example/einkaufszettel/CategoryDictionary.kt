package com.example.einkaufszettel

object CategoryDictionary {

    private val raw: List<Pair<Category, String>> = listOf(
        Category.FRUIT_VEG to """
            apfel, äpfel, birne, birnen, banane, bananen, orange, orangen, zitrone, zitronen,
            limette, limetten, mandarine, mandarinen, clementine, clementinen, traube, trauben,
            erdbeere, erdbeeren, himbeere, himbeeren, heidelbeeren, blaubeeren, kirschen,
            pflaume, pflaumen, pfirsich, pfirsiche, nektarine, nektarinen, melone, melonen,
            wassermelone, ananas, mango, mangos, kiwi, kiwis, avocado, avocados, granatapfel,
            tomate, tomaten, kirschtomaten, cherrytomaten, gurke, gurken, salatgurke, paprika,
            zucchini, aubergine, auberginen, karotte, karotten, möhre, möhren, kartoffel,
            kartoffeln, süßkartoffel, süßkartoffeln, zwiebel, zwiebeln, knoblauch, lauch, porree,
            salat, eisbergsalat, feldsalat, rucola, spinat, brokkoli, blumenkohl, kohlrabi, kohl,
            rotkohl, weißkohl, spitzkohl, rosenkohl, fenchel, sellerie, radieschen, rettich,
            champignons, pilze, kürbis, ingwer, petersilie, schnittlauch, basilikum, dill,
            koriander, minze, frühlingszwiebeln, spargel, rote bete, kräuter, mais, chicorée
        """,
        Category.DELI to """
            oliven, antipasti, hummus, kartoffelsalat, nudelsalat, fleischsalat, heringssalat,
            lachs, räucherlachs, forelle, fisch, krabben, garnelen, sushi, tzatziki, dip,
            pastete, feinkost
        """,
        Category.BAKERY to """
            brot, brötchen, semmel, semmeln, toast, toastbrot, baguette, croissant, croissants,
            brezel, brezeln, laugenbrötchen, laugenstange, vollkornbrot, mischbrot, kuchen,
            torte, muffins, berliner, donut, donuts, zopf, knäckebrot, aufbackbrötchen,
            fladenbrot, ciabatta, brötchen
        """,
        Category.COFFEE_TEA to """
            kaffee, kaffeebohnen, espresso, kaffeepulver, kaffeekapseln, kaffeepads,
            instantkaffee, filterkaffee, tee, grüntee, schwarztee, kräutertee, früchtetee,
            pfefferminztee, kamillentee, kakao, trinkschokolade
        """,
        Category.BREAKFAST to """
            müsli, cornflakes, haferflocken, cerealien, cereals, granola, marmelade, konfitüre,
            honig, nutella, nussnugatcreme, erdnussbutter, schokocreme, porridge,
            brotaufstrich, ahornsirup
        """,
        Category.ORGANIC to """
            alnatura, rapunzel, demeter, bioland, naturland, dennree, reformhaus, soja, sojamilch,
            reiswaffeln
        """,
        Category.STAPLES to """
            mehl, zucker, backpulver, hefe, vanillezucker, speisestärke, grieß, puderzucker,
            natron, paniermehl, semmelbrösel, backmischung, gelatine, rosinen, kokosraspeln
        """,
        Category.SWEETS to """
            schokolade, schokoriegel, gummibärchen, bonbons, bonbon, kekse, keks, lakritz,
            pralinen, riegel, süßigkeiten, waffeln, kaugummi, haribo, marshmallows
        """,
        Category.SPECIALTIES to """
            sojasauce, sojasoße, kokosmilch, tahini, currypaste, sambal, sriracha, miso,
            wasabi, taco, tacos, tortillas, wraps, falafel, nori, glasnudeln, tofu, tempeh
        """,
        Category.PASTA_RICE to """
            nudeln, spaghetti, penne, fusilli, tagliatelle, makkaroni, spätzle, lasagne, reis,
            basmatireis, risotto, couscous, bulgur, quinoa, pasta, tortellini, gnocchi,
            polenta, milchreis
        """,
        Category.OIL_SPICES to """
            öl, olivenöl, sonnenblumenöl, rapsöl, kokosöl, essig, balsamico, salz, pfeffer,
            paprikapulver, zimt, curry, oregano, thymian, rosmarin, muskat, gewürz, gewürze,
            senf, ketchup, mayonnaise, mayo, brühe, gemüsebrühe, sauce, soße, tomatenmark, bbq
        """,
        Category.DAIRY to """
            milch, vollmilch, hafermilch, sojamilch, mandelmilch, butter, margarine, sahne,
            schlagsahne, joghurt, naturjoghurt, quark, schmand, crème fraîche, creme fraiche,
            frischkäse, buttermilch, kefir, skyr, pudding, kondensmilch, h-milch
        """,
        Category.MEAT_CHEESE to """
            wurst, salami, schinken, speck, bacon, würstchen, bratwurst, leberwurst, mettwurst,
            fleisch, hackfleisch, hack, gehacktes, hähnchen, hähnchenbrust, hähnchenschenkel,
            schnitzel, steak, rindfleisch, schweinefleisch, geflügel, pute, putenbrust,
            käse, gouda, edamer, emmentaler, parmesan, mozzarella, feta, camembert, brie,
            schnittkäse, streukäse, reibekäse, leberkäse, aufschnitt, fleischwurst, lyoner
        """,
        Category.ALCOHOL to """
            bier, wein, rotwein, weißwein, rosé, sekt, prosecco, champagner, wodka, vodka,
            gin, rum, whisky, likör, schnaps, aperol, weizenbier, pils
        """,
        Category.DRINKS to """
            wasser, mineralwasser, sprudel, apfelsaft, orangensaft, saft, cola, limonade,
            fanta, sprite, eistee, energydrink, schorle, apfelschorle, tonic, ginger ale,
            softdrink, multivitaminsaft
        """,
        Category.EGGS to "ei, eier",
        Category.SNACKS to """
            chips, kartoffelchips, salzstangen, erdnüsse, nüsse, cashews, mandeln,
            studentenfutter, popcorn, cracker, kräcker, tortillachips, nachos, salzgebäck,
            nussmischung, pistazien, walnüsse, haselnüsse, trockenfrüchte
        """,
        Category.CANNED to """
            dosentomaten, gehackte tomaten, passierte tomaten, passata, kidneybohnen,
            kichererbsen, linsen, bohnen, thunfisch, sardinen, erbsen, sauerkraut,
            gewürzgurken, suppe, eintopf, ravioli, fertiggericht, dosen
        """,
        Category.HOUSEHOLD to """
            spülmittel, spülmaschinentabs, tabs, waschmittel, weichspüler, toilettenpapier,
            klopapier, küchenrolle, taschentücher, müllbeutel, müllsäcke, mülltüten,
            backpapier, alufolie, frischhaltefolie, gefrierbeutel, schwamm, schwämme,
            spüllappen, putzmittel, reiniger, allzweckreiniger, glasreiniger, entkalker,
            batterien, glühbirne, kerzen, servietten, papiertücher, kaffeefilter, teelichter,
            streichhölzer, feuerzeug, geschirrtücher, spülmaschinensalz
        """,
        Category.DRUGSTORE to """
            zahnpasta, zahnbürste, shampoo, duschgel, seife, deo, deodorant, creme, handcreme,
            bodylotion, sonnencreme, rasierer, rasierschaum, tampons, binden, slipeinlagen,
            wattepads, wattestäbchen, haargel, haarspray, haarfarbe, pflaster, tabletten,
            vitamine, kondome, mundwasser, zahnseide, lippenbalsam, nagellack, parfum,
            windeln, feuchttücher, haarspülung, gesichtscreme, make-up, mascara
        """,
        Category.FROZEN to """
            tiefkühlpizza, pizza, pommes, tiefkühlgemüse, eis, speiseeis, eiscreme,
            fischstäbchen, rahmspinat, blattspinat, tiefkühlbeeren, kroketten, nuggets,
            tiefkühl, gemüsepfanne, eiswürfel
        """
    )

    private val entries: Map<String, Category> = buildMap {
        for ((category, words) in raw) {
            words.split(",")
                .map { it.trim().lowercase() }
                .filter { it.isNotEmpty() }
                .forEach { put(it, category) }
        }
    }

    // Only these endings are trusted for compound words ("Ziegenmilch", "Geflügelwurst").
    // Deliberately no short or ambiguous words (lauch, wein, reis, ei, eis, öl ...).
    private val compoundHeads: List<String> = listOf(
        "milch", "wurst", "käse", "saft", "wasser", "brot", "brötchen", "salat", "kuchen",
        "tee", "bier", "suppe", "soße", "sauce", "creme", "nudeln", "brühe", "schokolade",
        "chips", "joghurt", "quark", "butter", "schinken", "fleisch", "müsli", "essig",
        "pizza", "kaffee", "sahne", "mehl"
    ).sortedByDescending { it.length }

    fun lookup(name: String): Category? {
        val key = InputParser.normalize(name)
        entries[key]?.let { return it }

        val words = key.split(" ", "-").filter { it.isNotEmpty() }
        if ("bio" in words) return Category.ORGANIC

        for (w in words.reversed()) entries[w]?.let { return it }

        for (w in words.reversed()) {
            val hit = compoundHeads.firstOrNull { w.length > it.length && w.endsWith(it) }
            if (hit != null) return entries[hit]
        }
        return null
    }
}
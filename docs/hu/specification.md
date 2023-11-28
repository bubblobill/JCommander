# Két paneles fájlkezelő applikáció - specifikáció

## Bevezetés

### A program célja

A program a _'80-as, '90-es évek_ méltán népszerű, **két paneles fájlkezelő** megoldásait hivatott visszaidézni
modern köntösben.

A két paneles fájlkezelők (dual-pane file managers) ötletének lényege, hogy
az applikáció felületén két külön panelen jelenik meg egy-egy nézete a fájlrendszernek,
mely tipikusan egy, a fájlrendszer hirearchiáját tükröző fából, egy, az aktuális munkamappa (working directory)
tartalmát mutató listából és egy címsorból áll.

A paneleken külön-külön, egymástól függetlenül navigálhatunk a fájlrendszerben, akár a fa, akár a lista elmeire
kattintunk, akár egy konkrét elérési út megadásával a címsorban.

Hasznos tulajdonsága - és egyben gyakori alkalmazása - az ilyen jellegű megoldásoknak, hogy fájlok másolása, illetőleg
mozgatása során az egymás melletti panelek sokkal átláthatóbban jelzik, hogy a művelet végrehajtása során honnan és hová
kerülnek a másolandó vagy áthelyezendő elemek.

## Use-case-ek

A program által **támogatott műveletek**:

 * navigálás **"lefelé"** (gyerek objektumok: mappa vagy csatolási pont)
 * navigálás **"felfelé"** (szülő objektumok: mappa, csatolási pont vagy gyökér elem)
 * navigálás az **előző munkamappába** (ahol a jelenlegibe navigálás előtt volt)
 * navigálás a **következő munkamappába** (ahol a visszanavigálás előtt volt)
 * munkamappa tartalmának **frissítése** (aktualizálása a fájlrendszer tartalmával)
 * fájlok **másolása** (a két panel által reprezentált mappa között)
 * fájlok **mozgatása**/**áthelyezése**
 * **beállítások módosítása**
   * fájlrendszer fa elrejtése/megjelenítése
   * ikonstílus kiválasztása

## Megoldás ötlete

### MVC-architektúra

A két panel könnyen modellezhető egy olyan objektumként, mely egységbe zárja a panel saját modelljét, nézetét és
vezérlőjét (**Model-View-Controller**).

Ennek a megoldásnak az a nagy előnye, hogy a modellben tárolt adatok megváltozására az **összes nézet automatikusan
frissül**, és ráadásul anélkül, hogy az adatok változtatásának helyén meg kéne adnunk, hogy melyik nézet hogyan végezze
el a frissítést. Így tehát tetszőlegesen sok nézettel bővíthető anélkül, hogy a változások kezelőkódját újra kéne írnunk.

### Modell (Model)

A modell tulajdonképpen egy munkamenetként is felfogható, ami magába foglalja a munkamenet aktuális munkamappáját és
annak történetét (history).

### Nézet (View)

A nézet három komponensből tevődik össze: fa (tree) és lista (list).

### Vezérlő (Controller)

A vezérlő feladata, hogy a nézetben érzékelt felhasználói interakciók hatására aktualizálja a modellben tárolt adatokat.

Ennél a viszonylag kis méretű architektúránál nem feltétlen indokolt, hogy az MVC egyes komponensein felül legyen még
egy összekötő modul is, melynek egyetlen feladata, hogy létrehozza és összekapcsolja a komponenseket, ezért a vezérlőnek
a feladata a modell és a nézet létrehozása is.

## Használt fájlformátum

### Konfigurációs fájl formátuma

A konfigurációs fájl az, aminek alapján a program képes perzisztens módon kezelni a felhasználónak a program kinézetére
és működésére vonatkozó sajátos igényeit (így a [Use-case-ek](#Use-case-ek)ben felsorolt opciókat) is.

A konfigurációs fájl minden sora egy-egy kulcs-érték párból áll, melyeket egy egyenlőség jel ('=') választ el egymástól.

#### Példa

```
SHOW_TREE_VIEW=true
ICON_STYLE=colorful
...
```

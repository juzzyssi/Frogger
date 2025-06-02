
General documentation:
    The building blocks of the model's "terrain" ( AKA "static primitives" ) deal with terrain grid-based processes through three layers: general management & specific functionality.

Tile:
    Tiles are abstract instances that operate as enhanced rectangles with the following upgrades:
        - Renderability: flexible support for visual implementations ( e.g. images, gifs ).
        - Interactivity: token provison for specified "interactions" ( see: Model > model > interactives ).

    Tiles are the fundation of all terrain generation. However, because of their abstract properties; only sub-classes of Tile are directly involved in the terrain's generation ( see: World > model > templates > statics > cell ).

Region:
    Regions operate as abstract collections & manipulators of tiles & "toys" ( see: Model > model > interactives > primitives ) given a certain set of "grids" or "tiles".

Terrain:
    W.I.P.
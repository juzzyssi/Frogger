
General documentation:
    ( Sub classes: Model > model > templates > statics )

    This package aims to address the functional infrastructure of the model's terrain logic from first principles.

Tile:
    "Tile" is the abstract & primitive class that rules & spread common functionality across sub-tiles. Tiles enhance java.awt.rectangle's functionality
    with the following upgrades:
        - Renderability: flexible support for visual implementations ( e.g. images, gifs ).
        - Interactivity: token provison for specified "interactions" ( see: Model > model > interactives ).

    IMPORTANT: Sub-tiles must provide a constructor with the following declared paramters: SubTile(Terrain, Vector). By deafult, differing behavior may
    trigger a "NoSuchMethodException" during the tile-instantiation process.

Region:
    ( Further documentation: Util > DimentionalList2D; Math > Vector )

    Regions operate as VIRTUAL collectors & mutators of a "Terrain" object's tiles by providing extensible and customizable processes of instancing while
    granting logic-consistency across the program.
    
    Often, vectors are the only suited method of parametrization across the programm; any form of further region parametrization is encouraged to exclusively
    rely in vectors and vectors only (exceptions should be handled carefully).

    Regions won't naturally generate "toys"; this behaviour must be overriden by subclasses individually.

    IMPORTANT: Sub-regions must provide a constructor with the following declared paramters: SubRegion( Collection<Vector>, Terrain). By deafult, differing
    behavior may trigger a "NoSuchMethodException" during the tile-instantiation process.

Terrain:
    Terrain instances can be compared to an interface dedicated to the management of all tile instances; they are responsible for bridging external (e.g.
    engine components) & internal functionality (static primitives).
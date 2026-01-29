INSERT INTO
    items (title, price, description)
VALUES (
        'Bonnet et Snood',
        40.00,
        'Ensemble bonnet et snood chaud et confortable, fait main avec de la laine de première qualité.'
    ),
    (
        'Lot de 6 lingettes',
        10.00,
        'Lot de 6 lingettes lavables et réutilisables, confectionnées à la main avec des tissus doux et respectueux de l''environnement'
    ),
    (
        'Ensemble de pochette et lingette',
        13.00,
        'Ensemble pratique comprenant une pochette de transport et une lingette lavable assortie, idéal pour vos déplacements et pour garder vos lingettes propres.'
    ),
    (
        'Pochette à lunette',
        20.00,
        'Pochette élégante et protectrice pour vos lunettes, fabriquée à la main avec des tissus de qualité.'
    ),
    (
        'Bandeau démaquillant',
        10.00,
        'Bandeau pour se démaquiller ou se faire un soin'
    ),
    (
        'Sac',
        30.00,
        'Sac en simili cuir et lin'
    ),
    (
        'Pochette ceinture',
        25.00,
        'Pochette de ceinture modulable avec différents motifs et couleurs'
    ),
    (
        'Tote Bag',
        20.00,
        'Tote Bag pratique et élégant, idéal pour vos sorties quotidiennes et vos courses, confectionné avec des matériaux de qualité'
    ),
    (
        'Pochette à Bouteilles',
        15.00,
        'Pochette pour porter vos bouteilles d''eau ou de vin avec élégance et praticité, idéale pour vos sorties ou vos courses.'
    );

INSERT INTO
    item_images (item_id, image_url, alt_text)
VALUES (
        1,
        'media/items/bonnetVueCote.jpg',
        'Bonnet porté sur la tête vue de côté'
    ),
    (
        1,
        'media/items/BonnetVueDevant.jpg',
        'Bonnet porté sur la tête vue de devant'
    ),
    (
        1,
        'media/items/bonnetVueHaut.jpg',
        'Bonnet porté sur la tête vue de haut'
    ),
    (
        1,
        'media/items/Snood.jpg',
        'Snood présenté sur le portant'
    ),
    (
        2,
        'media/items/Lingettelot.jpg',
        'Lot de 6 lingettes lavables et réutilisables'
    ),
    (
        3,
        'media/items/LingettePochette.jpg',
        'Lingette et pochette assortie'
    ),
    (
        4,
        'media/items/PochetteLunette.jpg',
        'Pochette à lunette'
    ),
    (
        5,
        'media/items/BandeauDemaquillant.jpg',
        'Bandeau démaquillant'
    ),
    (
        6,
        'media/items/SacCuirLinDevant.jpg',
        'Sac vue de devant'
    ),
    (
        6,
        'media/items/SacCuirLinDerriere.jpg',
        'Sac vue arrière'
    ),
    (
        6,
        'media/items/SacCuirLinInterieur.jpg',
        'Sac vue de l''intérieur'
    ),
    (
        7,
        'media/items/PochetteCeinturePorte.jpg',
        'Pochette de ceinture portée'
    ),
    (
        7,
        'media/items/PochetteCeintureVueArriere.jpg',
        'Vue arrière de la pochette de ceinture'
    ),
    (
        7,
        'media/items/PochetteMotifs.jpg',
        'Motifs différents'
    ),
    (
        8,
        'media/items/ToteBagParisInterieur.jpg',
        'Tote Bag de Paris interieur'
    ),
    (
        8,
        'media/items/ToteBagParisExterieur.jpg',
        'Tote Bag de Paris extérieur'
    ),
    (
        8,
        'media/items/ToteBagChat.jpg',
        'Tote Bag de Chat'
    ),
    (
        9,
        'media/items/Bouteille.jpg',
        'Pochette à bouteille'
    ),
    (
        9,
        'media/items/MotifsBouteille.jpg',
        'Différents motifs de pochette'
    );

INSERT INTO
    colors (name, image)
VALUES (
        'Beige',
        'media/colors/Beige.jpg'
    ),
    (
        'Blanc',
        'media/colors/Blanc.jpg'
    ),
    (
        'Bleu Clair',
        'media/colors/BleuClair.jpg'
    ),
    (
        'Bleu Canard',
        'media/colors/BleuCanard.webp'
    ),
    (
        'Bleu Gitane',
        'media/colors/BleuGitane.png'
    ),
    (
        'Cafe',
        'media/colors/Cafe.jpg'
    ),
    (
        'Celadon',
        'media/colors/Celadon.jpg'
    ),
    (
        'Cerise',
        'media/colors/Cerise.jpg'
    ),
    (
        'Ciel Gris',
        'media/colors/CielGris.jpg'
    ),
    (
        'Denim',
        'media/colors/Denim.jpg'
    ),
    (
        'Ecru',
        'media/colors/Ecru.webp'
    ),
    (
        'Framboise',
        'media/colors/Framboise.jpg'
    ),
    (
        'Fuchsia Pink',
        'media/colors/FuchsiaPink.jpg'
    ),
    (
        'Gris Moyen',
        'media/colors/GrisMoyen.jpg'
    ),
    (
        'Jaune Souffre',
        'media/colors/JauneSouffre.jpg'
    ),
    (
        'Love Pink',
        'media/colors/LovePink.jpg'
    ),
    (
        'Marine',
        'media/colors/Marine.webp'
    ),
    (
        'Noir',
        'media/colors/Noir.jpg'
    ),
    (
        'Rose Des Sables',
        'media/colors/RoseDesSables.webp'
    ),
    (
        'Rose Pale',
        'media/colors/RosePale.png'
    ),
    (
        'Rouge Cuivre',
        'media/colors/RougeCuivre.jpg'
    ),
    (
        'Vert Emeraude',
        'media/colors/VertEmeraude.webp'
    ),
    (
        'Vert Menthe',
        'media/colors/VertMenthe.jpg'
    );

INSERT INTO
    item_colors (item_id, color_id)
VALUES (1, 1),
    (1, 2),
    (1, 3),
    (1, 4),
    (1, 5),
    (1, 6),
    (1, 7),
    (1, 8),
    (7, 9),
    (7, 10),
    (7, 11),
    (7, 12);
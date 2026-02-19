package com.zttothers.frenchgrammar.data.local

import com.zttothers.frenchgrammar.data.models.Verb

object VerbDataGenerator {
    fun getDefaultVerbs(): List<Verb> = listOf(
        // 第一列
        Verb(infinitive = "aller", meaning = "去，到",
            je = "vais", tu = "vas", ilElle = "va", nous = "allons", vous = "allez", ilsElles = "vont",
            pastParticiple = "allé", relatedVerbs = "s'en aller"),

        Verb(infinitive = "avoir", meaning = "有",
            je = "ai", tu = "as", ilElle = "a", nous = "avons", vous = "avez", ilsElles = "ont",
            pastParticiple = "eu"),

        Verb(infinitive = "battre", meaning = "打",
            je = "bats", tu = "bats", ilElle = "bat", nous = "battons", vous = "battez", ilsElles = "battent",
            pastParticiple = "battu", relatedVerbs = "abattre, combattre, débattre"),

        Verb(infinitive = "boire", meaning = "喝",
            je = "bois", tu = "bois", ilElle = "boit", nous = "buvons", vous = "buvez", ilsElles = "boivent",
            pastParticiple = "bu"),

        Verb(infinitive = "conclure", meaning = "使結束",
            je = "conclus", tu = "conclus", ilElle = "conclut", nous = "concluons", vous = "concluez", ilsElles = "concluent",
            pastParticiple = "conclu", relatedVerbs = "exclure; inclure"),

        Verb(infinitive = "conduire", meaning = "駕駛，引導",
            je = "conduis", tu = "conduis", ilElle = "conduit", nous = "conduisons", vous = "conduisez", ilsElles = "conduisent",
            pastParticiple = "conduit", relatedVerbs = "réduire, introduire, produire, séduire, déduire, traduire"),

        Verb(infinitive = "connaître", meaning = "認識",
            je = "connais", tu = "connais", ilElle = "connaît", nous = "connaissons", vous = "connaissez", ilsElles = "connaissent",
            pastParticiple = "connu", relatedVerbs = "reconnaître"),

        Verb(infinitive = "construire", meaning = "建造",
            je = "construis", tu = "construis", ilElle = "construit", nous = "construisons", vous = "construisez", ilsElles = "construisent",
            pastParticiple = "construit", relatedVerbs = "instruire, détruire"),

        Verb(infinitive = "courir", meaning = "跑",
            je = "cours", tu = "cours", ilElle = "court", nous = "courons", vous = "courez", ilsElles = "courent",
            pastParticiple = "couru", relatedVerbs = "accourir, recourir, concourir, parcourir, secourir"),

        Verb(infinitive = "craindre", meaning = "害怕",
            je = "crains", tu = "crains", ilElle = "craint", nous = "craignons", vous = "craignez", ilsElles = "craignent",
            pastParticiple = "craint"),

        // 第二列
        Verb(infinitive = "croire", meaning = "相信",
            je = "crois", tu = "crois", ilElle = "croit", nous = "croyons", vous = "croyez", ilsElles = "croient",
            pastParticiple = "cru"),

        Verb(infinitive = "croître", meaning = "增長",
            je = "croîs", tu = "croîs", ilElle = "craît", nous = "croissons", vous = "croissez", ilsElles = "croissent",
            pastParticiple = "crû"),

        Verb(infinitive = "cueillir", meaning = "采摘",
            je = "cueille", tu = "cueilles", ilElle = "cueille", nous = "cueillons", vous = "cueillez", ilsElles = "cueillent",
            pastParticiple = "cueilli", relatedVerbs = "accueillir, recueillir"),

        Verb(infinitive = "défendre", meaning = "防衛",
            je = "défends", tu = "défends", ilElle = "défend", nous = "défendons", vous = "défendez", ilsElles = "défendent",
            pastParticiple = "défendu"),

        Verb(infinitive = "dépendre", meaning = "決定於",
            je = "dépends", tu = "dépends", ilElle = "dépend", nous = "dépendons", vous = "dépendez", ilsElles = "dépendent",
            pastParticiple = "dépendu", relatedVerbs = "pendre, suspendre"),

        Verb(infinitive = "descendre", meaning = "下來",
            je = "descends", tu = "descends", ilElle = "descend", nous = "descendons", vous = "descendez", ilsElles = "descendent",
            pastParticiple = "descendu"),

        Verb(infinitive = "devoir", meaning = "應該",
            je = "dois", tu = "dois", ilElle = "doit", nous = "devons", vous = "devez", ilsElles = "doivent",
            pastParticiple = "dû", relatedVerbs = "dû, dus, due, dues; 仅用于性数有关字"),

        Verb(infinitive = "dire", meaning = "說",
            je = "dis", tu = "dis", ilElle = "dit", nous = "disons", vous = "dites", ilsElles = "disent",
            pastParticiple = "dit"),

        Verb(infinitive = "écrire", meaning = "寫",
            je = "écris", tu = "écris", ilElle = "écrit", nous = "écrivons", vous = "écrivez", ilsElles = "écrivent",
            pastParticiple = "écrit", relatedVerbs = "décrire, inscrire, prescrire"),

        Verb(infinitive = "extraire", meaning = "提取",
            je = "extrais", tu = "extrais", ilElle = "extrait", nous = "extrayons", vous = "extrayez", ilsElles = "extraient",
            pastParticiple = "extrait", relatedVerbs = "traire, distraire, soustraire"),

        // 第三列
        Verb(infinitive = "faire", meaning = "做",
            je = "fais", tu = "fais", ilElle = "fait", nous = "faisons", vous = "faites", ilsElles = "font",
            pastParticiple = "fait", relatedVerbs = "refaire, satisfaire"),

        Verb(infinitive = "falloir", meaning = "應該",
            je = "", tu = "", ilElle = "faut", nous = "", vous = "", ilsElles = "",
            pastParticiple = "fallu"),

        Verb(infinitive = "fondre", meaning = "融化",
            je = "fonds", tu = "fonds", ilElle = "fond", nous = "fondons", vous = "fondez", ilsElles = "fondent",
            pastParticiple = "fondu", relatedVerbs = "confondre"),

        Verb(infinitive = "interdire", meaning = "禁止",
            je = "interdis", tu = "interdis", ilElle = "interdit", nous = "interdisons", vous = "interdisez", ilsElles = "interdisent",
            pastParticiple = "interdit", relatedVerbs = "interdites, interdisez"),

        Verb(infinitive = "joindre", meaning = "匯合",
            je = "joins", tu = "joins", ilElle = "joint", nous = "joignons", vous = "joignez", ilsElles = "joignent",
            pastParticiple = "joint", relatedVerbs = "rejoindre, conjoindre"),

        Verb(infinitive = "lire", meaning = "讀",
            je = "lis", tu = "lis", ilElle = "lit", nous = "lisons", vous = "lisez", ilsElles = "lisent",
            pastParticiple = "lu", relatedVerbs = "élire"),

        Verb(infinitive = "mentir", meaning = "撒謊",
            je = "mens", tu = "mens", ilElle = "ment", nous = "mentons", vous = "mentez", ilsElles = "mentent",
            pastParticiple = "menti"),

        Verb(infinitive = "mettre", meaning = "放",
            je = "mets", tu = "mets", ilElle = "met", nous = "mettons", vous = "mettez", ilsElles = "mettent",
            pastParticiple = "mis", relatedVerbs = "promettre, émettre, remettre, permettre"),

        Verb(infinitive = "mourir", meaning = "死",
            je = "meurs", tu = "meurs", ilElle = "meurt", nous = "mourons", vous = "mourez", ilsElles = "meurent",
            pastParticiple = "mort"),

        Verb(infinitive = "naître", meaning = "出生",
            je = "nais", tu = "nais", ilElle = "naît", nous = "naissons", vous = "naissez", ilsElles = "naissent",
            pastParticiple = "né"),

        // 第四列
        Verb(infinitive = "nuire", meaning = "對...有害",
            je = "nuis", tu = "nuis", ilElle = "nuit", nous = "nuisons", vous = "nuisez", ilsElles = "nuisent",
            pastParticiple = "nui"),

        Verb(infinitive = "offrir", meaning = "提供",
            je = "offre", tu = "offres", ilElle = "offre", nous = "offrons", vous = "offrez", ilsElles = "offrent",
            pastParticiple = "offert"),

        Verb(infinitive = "ouvrir", meaning = "打開",
            je = "ouvre", tu = "ouvres", ilElle = "ouvre", nous = "ouvrons", vous = "ouvrez", ilsElles = "ouvrent",
            pastParticiple = "ouvert", relatedVerbs = "couvrir, découvrir"),

        Verb(infinitive = "paraître", meaning = "有起來",
            je = "parais", tu = "parais", ilElle = "paraît", nous = "paraissons", vous = "paraissez", ilsElles = "paraissent",
            pastParticiple = "paru", relatedVerbs = "apparaître, disparaître"),

        Verb(infinitive = "partir", meaning = "離開",
            je = "pars", tu = "pars", ilElle = "part", nous = "partons", vous = "partez", ilsElles = "partent",
            pastParticiple = "parti"),

        Verb(infinitive = "peindre", meaning = "畫",
            je = "peins", tu = "peins", ilElle = "peint", nous = "peignons", vous = "peignez", ilsElles = "peignent",
            pastParticiple = "peint"),

        Verb(infinitive = "perdre", meaning = "失去",
            je = "perds", tu = "perds", ilElle = "perd", nous = "perdons", vous = "perdez", ilsElles = "perdent",
            pastParticiple = "perdu"),

        Verb(infinitive = "plaindre", meaning = "同情",
            je = "plains", tu = "plains", ilElle = "plaint", nous = "plaignons", vous = "plaignez", ilsElles = "plaignent",
            pastParticiple = "plaint"),

        Verb(infinitive = "plaire", meaning = "使...高興",
            je = "plais", tu = "plais", ilElle = "plaît", nous = "plaisons", vous = "plaisez", ilsElles = "plaisent",
            pastParticiple = "plu"),

        Verb(infinitive = "pleuvoir", meaning = "下雨",
            je = "", tu = "", ilElle = "pleut", nous = "", vous = "", ilsElles = "",
            pastParticiple = "plu"),

        // 第五列
        Verb(infinitive = "pouvoir", meaning = "能夠",
            je = "peux", tu = "peux", ilElle = "peut", nous = "pouvons", vous = "pouvez", ilsElles = "peuvent",
            pastParticiple = "pu"),

        Verb(infinitive = "prendre", meaning = "拿取",
            je = "prends", tu = "prends", ilElle = "prend", nous = "prenons", vous = "prenez", ilsElles = "prennent",
            pastParticiple = "pris", relatedVerbs = "apprendre, surprendre"),

        Verb(infinitive = "recevoir", meaning = "收到",
            je = "recois", tu = "recois", ilElle = "reçoit", nous = "recevons", vous = "recevez", ilsElles = "reçoivent",
            pastParticiple = "reçu", relatedVerbs = "concevoir, décevoir, percevoir, apercevoir"),

        Verb(infinitive = "rendre", meaning = "歸還",
            je = "rends", tu = "rends", ilElle = "rend", nous = "rendons", vous = "rendez", ilsElles = "rendent",
            pastParticiple = "rendu"),

        Verb(infinitive = "répondre", meaning = "回答",
            je = "réponds", tu = "réponds", ilElle = "répond", nous = "répondons", vous = "répondez", ilsElles = "répondent",
            pastParticiple = "répondu"),

        Verb(infinitive = "résoudre", meaning = "解決",
            je = "résous", tu = "résous", ilElle = "résout", nous = "résolvons", vous = "résolvez", ilsElles = "résolvent",
            pastParticiple = "résolu", relatedVerbs = "dissoudre"),

        Verb(infinitive = "rire", meaning = "笑",
            je = "ris", tu = "ris", ilElle = "rit", nous = "rions", vous = "riez", ilsElles = "rient",
            pastParticiple = "ri", relatedVerbs = "sourire"),

        Verb(infinitive = "rompre", meaning = "折斷",
            je = "romps", tu = "romps", ilElle = "rompt", nous = "rompons", vous = "rompez", ilsElles = "rompent",
            pastParticiple = "rompu", relatedVerbs = "interrompe, corrompre"),

        Verb(infinitive = "s'asseoir", meaning = "坐下",
            je = "m'assieds", tu = "t'assieds", ilElle = "s'assied", nous = "asseyons", vous = "asseyez", ilsElles = "s'asseyent",
            pastParticiple = "assis"),

        Verb(infinitive = "savoir", meaning = "知道",
            je = "sais", tu = "sais", ilElle = "sait", nous = "savons", vous = "savez", ilsElles = "savent",
            pastParticiple = "su"),

        // 第六列
        Verb(infinitive = "sentir", meaning = "感到",
            je = "sens", tu = "sens", ilElle = "sent", nous = "sentons", vous = "sentez", ilsElles = "sentent",
            pastParticiple = "senti", relatedVerbs = "ressentir, pressentir, consentir"),

        Verb(infinitive = "servir", meaning = "服務",
            je = "sers", tu = "sers", ilElle = "sert", nous = "servons", vous = "servez", ilsElles = "servent",
            pastParticiple = "servi"),

        Verb(infinitive = "sortir", meaning = "出去",
            je = "sors", tu = "sors", ilElle = "sort", nous = "sortons", vous = "sortez", ilsElles = "sortent",
            pastParticiple = "sorti", relatedVerbs = "ressortir"),

        Verb(infinitive = "souffrir", meaning = "忍受",
            je = "souffre", tu = "souffres", ilElle = "souffre", nous = "souffrons", vous = "souffrez", ilsElles = "souffrent",
            pastParticiple = "souffert"),

        Verb(infinitive = "suffir", meaning = "足夠",
            je = "suffis", tu = "suffis", ilElle = "suffit", nous = "suffisons", vous = "suffisez", ilsElles = "suffisent",
            pastParticiple = "suffi"),

        Verb(infinitive = "suivre", meaning = "跟隨",
            je = "suis", tu = "suis", ilElle = "suit", nous = "suivons", vous = "suivez", ilsElles = "suivent",
            pastParticiple = "suivi", relatedVerbs = "poursuivre"),

        Verb(infinitive = "taire", meaning = "使緘默",
            je = "tais", tu = "tais", ilElle = "tait", nous = "taisons", vous = "taisez", ilsElles = "taisent",
            pastParticiple = "tu"),

        Verb(infinitive = "teindre", meaning = "染色",
            je = "teins", tu = "teins", ilElle = "teint", nous = "teignons", vous = "teignez", ilsElles = "teignent",
            pastParticiple = "teint", relatedVerbs = "atteindre, éteindre, astreindre, restreindre"),

        Verb(infinitive = "tendre", meaning = "伸展",
            je = "tends", tu = "tends", ilElle = "tend", nous = "tendons", vous = "tendez", ilsElles = "tendent",
            pastParticiple = "tendu", relatedVerbs = "attendre, détendre, entendre, étendre, prétendre"),

        Verb(infinitive = "tenir", meaning = "拿，持",
            je = "tiens", tu = "tiens", ilElle = "tient", nous = "tenons", vous = "tenez", ilsElles = "tiennent",
            pastParticiple = "tenu", relatedVerbs = "abstenir, obtenir, soutenir, contenir, détenir, retenir"),

        // 第七列
        Verb(infinitive = "vaincre", meaning = "征服",
            je = "vaincs", tu = "vaincs", ilElle = "vainc", nous = "vainquons", vous = "vainquez", ilsElles = "vainquent",
            pastParticiple = "vaincu", relatedVerbs = "convaincre"),

        Verb(infinitive = "valoir", meaning = "值",
            je = "vaux", tu = "vaux", ilElle = "vaut", nous = "valons", vous = "valez", ilsElles = "valent",
            pastParticiple = "valu", relatedVerbs = "prévaloir"),

        Verb(infinitive = "vendre", meaning = "售",
            je = "vends", tu = "vends", ilElle = "vend", nous = "vendons", vous = "vendez", ilsElles = "vendent",
            pastParticiple = "vendu"),

        Verb(infinitive = "venir", meaning = "來",
            je = "viens", tu = "viens", ilElle = "vient", nous = "venons", vous = "venez", ilsElles = "viennent",
            pastParticiple = "venu", relatedVerbs = "devenir, revenir, souvenir, prévenir, convenir, subvenir, parvenir, intervenir, maintenir, provenir, appartenir"),

        Verb(infinitive = "vivre", meaning = "生活",
            je = "vis", tu = "vis", ilElle = "vit", nous = "vivons", vous = "vivez", ilsElles = "vivent",
            pastParticiple = "vécu", relatedVerbs = "survivre"),

        Verb(infinitive = "voir", meaning = "看見",
            je = "vois", tu = "vois", ilElle = "voit", nous = "voyons", vous = "voyez", ilsElles = "voient",
            pastParticiple = "vu", relatedVerbs = "revoir, prévoir"),

        Verb(infinitive = "vouloir", meaning = "想要",
            je = "veux", tu = "veux", ilElle = "veut", nous = "voulons", vous = "voulez", ilsElles = "veulent",
            pastParticiple = "voulu"),

        // 常用動詞 (Frequently used verbs)
        Verb(infinitive = "bouillir", meaning = "沸騰",
            je = "bous", tu = "bous", ilElle = "bout", nous = "bouillons", vous = "bouillez", ilsElles = "bouillent",
            pastParticiple = "bouilli"),

        Verb(infinitive = "conquérir", meaning = "征服",
            je = "conquiers", tu = "conquiers", ilElle = "conquiert", nous = "conquérons", vous = "conquérez", ilsElles = "conquièrent",
            pastParticiple = "conquis", relatedVerbs = "acquérir, recquérir"),

        Verb(infinitive = "coudre", meaning = "縫",
            je = "couds", tu = "couds", ilElle = "coud", nous = "cousons", vous = "cousez", ilsElles = "cousent",
            pastParticiple = "cousu"),

        Verb(infinitive = "haïr", meaning = "恨",
            je = "haïs", tu = "haïs", ilElle = "haît", nous = "haïssons", vous = "haïssez", ilsElles = "haïssent",
            pastParticiple = "haï"),

        Verb(infinitive = "vêtir", meaning = "給...穿衣",
            je = "vêts", tu = "vêts", ilElle = "vêt", nous = "vêtons", vous = "vêtez", ilsElles = "vêtent",
            pastParticiple = "vêtu")
    )
}


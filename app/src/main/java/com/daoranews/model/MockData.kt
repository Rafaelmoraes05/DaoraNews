package com.daoranews.model

import com.daoranews.model.Article

// 1. DADOS MOCKADOS CENTRALIZADOS
val mockArticles = listOf(
    Article(
        id = 1,
        title = "Nova Era da F1: Carros de 2025 Revelados!",
        snippet = "As mudanças aerodinâmicas prometem corridas mais disputadas...",
        author = "Ana R.",
        date = "20 de Maio",
        readTimeInMinutes = 5,
        fullContent = """
            A Fórmula 1 revelou hoje os detalhes técnicos dos carros da temporada de 2025, marcando o início de uma nova era para a categoria. As principais mudanças se concentram na aerodinâmica, com o objetivo de permitir que os carros sigam uns aos outros mais de perto, aumentando as oportunidades de ultrapassagem.

            O assoalho será redesenhado para gerar uma maior porcentagem do downforce total, reduzindo o ar sujo lançado para o carro de trás. Além disso, as asas dianteira e traseira foram simplificadas. "Nosso objetivo é criar um espetáculo melhor para os fãs", disse o diretor técnico da F1. "Acreditamos que essas regras tornarão as corridas mais imprevisíveis e emocionantes."

            As equipes agora têm um grande desafio pela frente para interpretar o novo regulamento e encontrar as melhores soluções. Os testes de túnel de vento começarão no próximo mês, e a expectativa é que vejamos conceitos de design muito diferentes entre as equipes.
        """.trimIndent()
    ),
    Article(
        id = 2,
        title = "Nando Norris, da McLaren, Domina em GP de Mônaco",
        snippet = "Uma vitória histórica nas ruas do principado para o jovem piloto.",
        author = "Carlos S.",
        date = "19 de Maio",
        readTimeInMinutes = 7,
        fullContent = "Em uma performance magistral, Lando Norris conquistou sua primeira vitória no Grande Prêmio de Mônaco..."
    ),
    Article(
        id = 3,
        title = "O Impacto do 5G na Indústria 4.0",
        snippet = "Velocidade e baixa latência estão transformando as fábricas.",
        author = "Tech Journal",
        date = "18 de Maio",
        readTimeInMinutes = 8,
        fullContent = "A quinta geração de redes móveis, conhecida como 5G, está começando a ser implementada em larga escala e seu impacto na Indústria 4.0 é imenso..."
    ),
    Article(
        id = 4,
        title = "Como a IA está redefinindo o mercado de trabalho",
        snippet = "Novas profissões surgem enquanto outras se adaptam à automação.",
        author = "Bia S.",
        date = "17 de Maio",
        readTimeInMinutes = 6,
        fullContent = "A Inteligência Artificial (IA) não é mais um conceito de ficção científica. Ela já está presente em diversas áreas do nosso dia a dia e está transformando radicalmente o mercado de trabalho..."
    )
)

// Lista separada para simular favoritos, para a ListPage
val savedArticlesMock = listOf(mockArticles[1], mockArticles[3])

fun getArticleById(id: Int): Article? {
    return mockArticles.find { it.id == id }
}
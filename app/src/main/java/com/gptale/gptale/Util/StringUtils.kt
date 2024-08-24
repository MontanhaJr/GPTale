package com.gptale.gptale.Util

import com.gptale.gptale.models.ParagraphModel

object StringUtils {
    fun defaultPrompt(title: String, gender: String, paragraphCount: Int): String {
        return "Vamos jogar uma história interativa. O tema da história é $title. O gênero textual é $gender. " +
                "Comece a história e a cada parágrafo da história, me dê 4 opções para escolher e continuar a história. " +
                "Sempre inicie as opções exatamente com 'Opção 1: ' ou 'Opção 2: ' ou 'Opção 3: ' ou 'Opção 4: '. " +
                "Qualquer opção que eu escolher deve ser válida, nunca peça para escolher outra opção." +
                "A história deverá continuar até que as quatro opções sejam enviadas $paragraphCount vezes. " +
                "No último parágrafo explicite que a história acabou. " +
                "As continuações da história só devem ser enviadas quando solicitado pelo usuário. " +
                "Não envie a continuação da história na primeira mensagem, envie apenas o parágrafo e as 4 opções para o usuário escolher." +
                "Não encerre a história antes de interagir com as opções por $paragraphCount vezes" +
                "Deve sempre finalizar com \"FIM\""
    }

    fun optionSelected(option: Int, finalize: Boolean): String {
        return if (finalize.not()) "Opção $option"
        else "Opção $option. Finalize a história com essa opção e não envie mais opções."
    }

    fun parseResponse(response: String): ParagraphModel {
        val paragraphRegex = Regex("^(.*?)(?=(Opção \\d+:|$))", RegexOption.DOT_MATCHES_ALL)
        val optionsRegex = Regex("(Opção \\d+: .*?\\.)", RegexOption.MULTILINE)
        val questionRegex = Regex("Opção 4: .*?\\.(.*?\\?)$", RegexOption.DOT_MATCHES_ALL)

        val paragraphMatch = paragraphRegex.find(response)
        val paragraph = paragraphMatch?.groupValues?.get(1)?.trim() ?: ""

        val optionsMatches = optionsRegex.findAll(response)
        val options = optionsMatches.map { it.groupValues[1].trim() }.toList()

        val questionMatch = questionRegex.find(response)
        val question = questionMatch?.groupValues?.get(1)?.trim()

        return ParagraphModel(paragraph, options, question)
    }
}
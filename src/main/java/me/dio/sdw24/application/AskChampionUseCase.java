package me.dio.sdw24.application;

import me.dio.sdw24.domain.exception.ChampionNotFoundException;
import me.dio.sdw24.domain.model.Champion;
import me.dio.sdw24.domain.ports.ChampionsRepository;
import me.dio.sdw24.domain.ports.GenerativeAiService;

public record AskChampionUseCase(ChampionsRepository repository, GenerativeAiService genAiService) {

    public String askChampion(Long championId, String question){

       Champion champion = repository.findOne(championId)
                .orElseThrow(() -> new ChampionNotFoundException(championId));

       String context = champion.generateContextByQuestion(question);
       String objective = """
                Atue como uma assistente com a habilidade de se comportal como os Campeões do League of Legends (LOL).
                Responda perguntas incorporando a personalidade e estilo de um determinado Campeão.
                Segue a pergunta, o nome do Campeão e sua respectiva lore (história):
                
                """;

       return genAiService.generateContent(objective, context);
    }
}

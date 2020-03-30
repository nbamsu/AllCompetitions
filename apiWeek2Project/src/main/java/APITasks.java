import AllNameOfLigue.CompetitionPojo;
import EnglandMidfield.MidfieldPojo;
import ToScorers.TopScorerPojo;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Assert;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by nurkulov 12/26/19
 */
public class APITasks {
    private static HttpClient httpClient;
    private static URIBuilder uriBuilder;
    private static HttpGet httpGet;
    private static HttpResponse response;
    private static ObjectMapper objectMapper;
    private static final String TOKEN = "a82e659c8fad4376843b7fffc5f366d3";
    private static final String KEY = "X-Auth-Token";
    private static final String JSON = "application/json";
    private static final String SCHEME = "https";
    private static final String HOST = "api.football-data.org";


    /*
     * GET all soccer team names listed in given resource
     * Deserialization type: Pojo
     */
    public static List<String> getAllTeams() throws URISyntaxException, IOException {
        httpClient = HttpClientBuilder.create().build();
        uriBuilder = new URIBuilder();
        uriBuilder.setScheme(SCHEME)
                .setHost(HOST)
                .setPath("v2/teams");
        httpGet = new HttpGet(uriBuilder.build());

        httpGet.setHeader("Accept", JSON);
        httpGet.setHeader(KEY, TOKEN);
        response = httpClient.execute(httpGet);
        objectMapper = new ObjectMapper();
        System.out.println(response.getStatusLine().getStatusCode());
        System.out.println(response.getEntity().getContentType().getValue());
        Assert.assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode());
        Assert.assertTrue(response.getEntity().getContentType().getValue().contains(JSON));

        TeamPojo getAllTeam = objectMapper.readValue(response.getEntity().getContent(), TeamPojo.class);

        List<String> teams = new ArrayList<>();
        int count = 0;
        for (int i = 0; i < getAllTeam.getTeams().size(); i++) {
            teams.add(getAllTeam.getTeams().get(i).getName());
            count++;
            System.out.println(teams.get(i));
        }
        System.out.println(count);
        return teams;

    }

    /*
     * GET names of all goalkeepers from England team
     * note: England team id is 66
     * Deserialization type: TypeReference
     */
    public static List<String> getAllGoalkeepers() throws URISyntaxException, IOException {
        httpClient = HttpClientBuilder.create().build();
        uriBuilder = new URIBuilder();
        uriBuilder.setScheme(SCHEME)
                .setHost(HOST)
                .setPath("v2/teams/66");
        httpGet = new HttpGet(uriBuilder.build());

        httpGet.setHeader("Accept", JSON);
        httpGet.setHeader(KEY, TOKEN);
        response = httpClient.execute(httpGet);
        objectMapper = new ObjectMapper();
        System.out.println(response.getStatusLine().getStatusCode());
        System.out.println(response.getEntity().getContentType().getValue());
        Assert.assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode());
        Assert.assertTrue(response.getEntity().getContentType().getValue().contains(JSON));

        Map<String,Object> teamPojo = objectMapper.readValue(response.getEntity().getContent(), new TypeReference<Map<String,Object>>(){});
        List<String> goalKeepers = new ArrayList<>();
        List<Map<String, Object>> squad=(List<Map<String, Object>>)teamPojo.get("squad");
        for (int i = 0; i < squad.size()-1; i++) {
            if (squad.get(i).get("position").toString().equalsIgnoreCase("goalkeeper")) {
                goalKeepers.add(squad.get(i).get("name").toString());
            }
        }
        System.out.println(goalKeepers);
        return goalKeepers;



    }

    /*
     * GET names of all defenders from England team
     * note: England team id is 66
     * Deserialization type: TypeReference
     */
    public static List<String> getDefenders() throws URISyntaxException, IOException {
        httpClient = HttpClientBuilder.create().build();
        uriBuilder = new URIBuilder();
        uriBuilder.setScheme(SCHEME)
                .setHost(HOST)
                .setPath("v2/teams/66");
        httpGet = new HttpGet(uriBuilder.build());

        httpGet.setHeader("Accept", JSON);
        httpGet.setHeader(KEY, TOKEN);
        response = httpClient.execute(httpGet);
        objectMapper = new ObjectMapper();
        System.out.println(response.getStatusLine().getStatusCode());
        System.out.println(response.getEntity().getContentType().getValue());
        Assert.assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode());
        Assert.assertTrue(response.getEntity().getContentType().getValue().contains(JSON));

        MidfieldPojo teamPojo = objectMapper.readValue(response.getEntity().getContent(), MidfieldPojo.class);
        List<String> defenderName = new ArrayList<>();
        for (int i = 0; i < teamPojo.getSquad().size()-1; i++) {
            if (teamPojo.getSquad().get(i).getPosition().equalsIgnoreCase("defender")) {
                defenderName.add(teamPojo.getSquad().get(i).getName());
            }
        }
        System.out.println(defenderName);
        return defenderName;
    }

    /*
     * GET names of all midfielders from England team
     * note: England team id is 66
     * Deserialization type: Pojo
     */
    public static List<String> getMidfielders() throws IOException, URISyntaxException {
        httpClient = HttpClientBuilder.create().build();
        uriBuilder = new URIBuilder();
        uriBuilder.setScheme(SCHEME)
                .setHost(HOST)
                .setPath("v2/teams/66");
        httpGet = new HttpGet(uriBuilder.build());

        httpGet.setHeader("Accept", JSON);
        httpGet.setHeader(KEY, TOKEN);
        response = httpClient.execute(httpGet);
        objectMapper = new ObjectMapper();
        System.out.println(response.getStatusLine().getStatusCode());
        System.out.println(response.getEntity().getContentType().getValue());
        Assert.assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode());
        Assert.assertTrue(response.getEntity().getContentType().getValue().contains(JSON));

        MidfieldPojo teamPojo = objectMapper.readValue(response.getEntity().getContent(), MidfieldPojo.class);
        List<String> midfiledName = new ArrayList<>();
        for (int i = 0; i < teamPojo.getSquad().size()-1; i++) {
            if (teamPojo.getSquad().get(i).getPosition().equalsIgnoreCase("midfielder")) {
                midfiledName.add(teamPojo.getSquad().get(i).getName());
            }
        }
        System.out.println(midfiledName);
        return midfiledName;
    }

    /*
     * GET names of all midfielders from England team whose country is Brazil
     * note: England team id is 66
     * Deserialization type: Pojo
     */
    public static List<String> getMidfielderFromBrazil() throws URISyntaxException, IOException {
        httpClient = HttpClientBuilder.create().build();
        uriBuilder = new URIBuilder();
        uriBuilder.setScheme(SCHEME)
                .setHost(HOST)
                .setPath("v2/teams/66");
        httpGet = new HttpGet(uriBuilder.build());

        httpGet.setHeader("Accept", JSON);
        httpGet.setHeader(KEY, TOKEN);
        response = httpClient.execute(httpGet);
        objectMapper = new ObjectMapper();
        System.out.println(response.getStatusLine().getStatusCode());
        System.out.println(response.getEntity().getContentType().getValue());
        Assert.assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode());
        Assert.assertTrue(response.getEntity().getContentType().getValue().contains(JSON));

        MidfieldPojo teamPojo = objectMapper.readValue(response.getEntity().getContent(), MidfieldPojo.class);
        List<String> midfielderFromBrazil = new ArrayList<>();
        for (int i = 0; i < teamPojo.getSquad().size()-1; i++) {
            if (teamPojo.getSquad().get(i).getPosition().equalsIgnoreCase("midfielder")&&
            teamPojo.getSquad().get(i).getCountryOfBirth().equalsIgnoreCase("brazil")) {
                midfielderFromBrazil.add(teamPojo.getSquad().get(i).getName());
            }
        }
        System.out.println(midfielderFromBrazil);
        return midfielderFromBrazil;
    }

    /*
     * GET names of all attackers from England team whose origin country is England
     * note: England team id is 66
     * Deserialization type: Pojo
     */
    public static List<String> getAttackerFromEngland() throws URISyntaxException, IOException {
        httpClient = HttpClientBuilder.create().build();
        uriBuilder = new URIBuilder();
        uriBuilder.setScheme(SCHEME)
                .setHost(HOST)
                .setPath("v2/teams/66");
        httpGet = new HttpGet(uriBuilder.build());

        httpGet.setHeader("Accept", JSON);
        httpGet.setHeader(KEY, TOKEN);
        response = httpClient.execute(httpGet);
        objectMapper = new ObjectMapper();
        System.out.println(response.getStatusLine().getStatusCode());
        System.out.println(response.getEntity().getContentType().getValue());
        Assert.assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode());
        Assert.assertTrue(response.getEntity().getContentType().getValue().contains(JSON));

        MidfieldPojo teamPojo = objectMapper.readValue(response.getEntity().getContent(), MidfieldPojo.class);
        List<String> attackersFromEngland = new ArrayList<>();
        for (int i = 0; i < teamPojo.getSquad().size()-1; i++) {
            if (teamPojo.getSquad().get(i).getPosition().equalsIgnoreCase("attacker")&&
                    teamPojo.getSquad().get(i).getCountryOfBirth().equalsIgnoreCase("england")) {
                attackersFromEngland.add(teamPojo.getSquad().get(i).getName());
            }
        }
        System.out.println(attackersFromEngland);
        return attackersFromEngland;
    }

    /*
     * GET name of Spain team coach
     * note: Spain team id is 77
     * Deserialization type: Pojo
     */
    public static List<String> getSpainCoach() throws URISyntaxException, IOException {
        httpClient = HttpClientBuilder.create().build();
        uriBuilder = new URIBuilder();
        uriBuilder.setScheme(SCHEME)
                .setHost(HOST)
                .setPath("v2/teams/77");
        httpGet = new HttpGet(uriBuilder.build());

        httpGet.setHeader("Accept", JSON);
        httpGet.setHeader(KEY, TOKEN);
        response = httpClient.execute(httpGet);
        objectMapper = new ObjectMapper();
        System.out.println(response.getStatusLine().getStatusCode());
        System.out.println(response.getEntity().getContentType().getValue());
        Assert.assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode());
        Assert.assertTrue(response.getEntity().getContentType().getValue().contains(JSON));

        MidfieldPojo teamPojo = objectMapper.readValue(response.getEntity().getContent(), MidfieldPojo.class);
        List<String> coachFromSpain = new ArrayList<>();
        for (int i = 0; i < teamPojo.getSquad().size(); i++) {
            if (teamPojo.getSquad().get(i).getRole().equalsIgnoreCase("COACH")&&
                    teamPojo.getSquad().get(i).getCountryOfBirth().equalsIgnoreCase("spain")) {
                coachFromSpain.add(teamPojo.getSquad().get(i).getName());
            }
        }
        System.out.println(coachFromSpain);
        return coachFromSpain;
    }

    public static List<String> getAllCompetitions() throws URISyntaxException, IOException {
        httpClient = HttpClientBuilder.create().build();
        uriBuilder = new URIBuilder();
        uriBuilder.setScheme(SCHEME)
                .setHost(HOST)
                .setPath("v2/competitions");
        httpGet = new HttpGet(uriBuilder.build());

        httpGet.setHeader("Accept", JSON);
        httpGet.setHeader(KEY, TOKEN);
        response = httpClient.execute(httpGet);
        objectMapper = new ObjectMapper();
        System.out.println(response.getStatusLine().getStatusCode());
        System.out.println(response.getEntity().getContentType().getValue());
        Assert.assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode());
        Assert.assertTrue(response.getEntity().getContentType().getValue().contains(JSON));

        //CompetitionPojo allCompetitionsPojo=objectMapper.readValue(response.getEntity().getContent(), CompetitionPojo.class);
        CompetitionPojo allCompetitionsPojo=objectMapper.readValue(response.getEntity().getContent(),CompetitionPojo.class);
        List<String> allCompetition=new ArrayList();
        for (int i=0;i<allCompetitionsPojo.getCompetitions().size();i++){
            allCompetition.add(allCompetitionsPojo.getCompetitions().get(i).getName());
            //Playoffs 1/2
        }
        for (int i = 0; i < allCompetitionsPojo.getCompetitions().size(); i++) {
            System.out.print("\"" + allCompetitionsPojo.getCompetitions().get(i).getName() + "\"" + ",");
        }
        //System.out.println(allCompetition);
        return allCompetition;

    }

    /*
     * GET names of second highest scorrer from competitions of 2000 season
     * note: endpoint for competitions: `competitions/<year>/
     * note: endpoint for scorers: `competitions/<year>/scorers`
     * Deserialization type: Pojo and TypeReference
     */
    public static List<String> getSecondHighestScorer() throws URISyntaxException, IOException {

        httpClient = HttpClientBuilder.create().build();
        uriBuilder = new URIBuilder();
        uriBuilder.setScheme(SCHEME)
                .setHost(HOST)
                .setPath("v2/competitions/2000/scorers");
        httpGet = new HttpGet(uriBuilder.build());

        httpGet.setHeader("Accept", JSON);
        httpGet.setHeader(KEY, TOKEN);
        response = httpClient.execute(httpGet);
        objectMapper = new ObjectMapper();
        System.out.println(response.getStatusLine().getStatusCode());
        System.out.println(response.getEntity().getContentType().getValue());
        Assert.assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode());
        Assert.assertTrue(response.getEntity().getContentType().getValue().contains(JSON));


//        objectMapper = new ObjectMapper();
//        Map<String, Object> comp2000 = objectMapper.readValue(response.getEntity().getContent(),
//                new TypeReference<Map<String, Object>>() {
//                });
//        List<String> playerName = new ArrayList<>();
//        List<Integer> scores = new ArrayList<>();
//        List<Map<String, Object>> scorers = (List<Map<String, Object>>) comp2000.get("scorers");
//        for (int i = 0; i < scorers.size(); i++) {
//            Map<String, Object> players = (Map<String, Object>) scorers.get(i).get("player");
//           playerName.add(players.get("name").toString());
//            scores.add((Integer) scorers.get(i).get("numberOfGoals"));
//        }
//
//        List<String> allCompetitions = new ArrayList<>();
//        int max = scores.get(0);
//        int min = scores.get(scores.size() - 1);
//        int secondMax = 0;
//        for(int i=0;i<scores.size();i++) {
//            if (scores.get(i) != max) {
//                secondMax = scores.get(i);
//                break;
//            }
//        }
//        for(int i=0;i<scores.size();i++){
//            if(scores.get(i)==secondMax){
//                allCompetitions.add(playerName.get(i));
//            }
//        }
//        System.out.println(allCompetitions);

        TopScorerPojo scores = objectMapper.readValue(response.getEntity().getContent(), TopScorerPojo.class);
        List<String> footballer = new ArrayList<>();
        List<Integer> goals = new ArrayList<>();
        for (int i = 0; i < scores.getScorers().size(); i++) {
            footballer.add(scores.getScorers().get(i).getPlayer().getName());
            goals.add(scores.getScorers().get(i).getNumberOfGoals());
        }
        int max = goals.get(0);
        int secondMax = 0;
        for (int i = 0; i < goals.size(); i++) {
            if (goals.get(i) != max) {
                secondMax = goals.get(i);
                break;
            }
        }
        List<String> secondHighestScorers = new ArrayList<>();
        for (int i = 0; i < goals.size(); i++) {
            if (goals.get(i) == secondMax) {
                secondHighestScorers.add(footballer.get(i));
            }
        }
        System.out.println(secondHighestScorers);
        return secondHighestScorers;


    }
}

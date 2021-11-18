package tests;

import com.robenhood.data.JSON;

public class JSONMain {
    public static void main(String[] args) {
        String json = "{\n" +
                "    \"banned\": [\n" +
                "        338436316544892929,\n" +
                "        744758489230082198\n" +
                "    ],\n" +
                "    \"registered\": {\n" +
                "        \"138027430693568512\": {\n" +
                "            \"username\": \"bmorr\",\n" +
                "            \"platform\": \"steam\",\n" +
                "            \"discord_user\": \"Bmorr\",\n" +
                "            \"points\": 801\n" +
                "        },\n" +
                "        \"391423004334358529\": {\n" +
                "            \"username\": \"CaptainPlasmo\",\n" +
                "            \"platform\": \"xbl\",\n" +
                "            \"discord_user\": \"CaptainPlasmo\",\n" +
                "            \"points\": 801\n" +
                "        },\n" +
                "        \"481821385447047168\": {\n" +
                "            \"username\": \"RoadSauce\",\n" +
                "            \"platform\": \"steam\",\n" +
                "            \"discord_user\": \"RoadSauce | Jesus\",\n" +
                "            \"points\": 500\n" +
                "        },\n" +
                "        \"240600351625969669\": {\n" +
                "            \"username\": \"choynoy\",\n" +
                "            \"platform\": \"epic\",\n" +
                "            \"discord_user\": \"TheTrout\",\n" +
                "            \"points\": 800\n" +
                "        },\n" +
                "        \"336146049053753346\": {\n" +
                "            \"username\": \"DolphinoRL\",\n" +
                "            \"platform\": \"steam\",\n" +
                "            \"discord_user\": \"Dolphino\",\n" +
                "            \"points\": 850\n" +
                "        },\n" +
                "        \"389086430644928513\": {\n" +
                "            \"username\": \"Is_Taka\",\n" +
                "            \"platform\": \"epic\",\n" +
                "            \"discord_user\": \"Taka\",\n" +
                "            \"points\": 801\n" +
                "        },\n" +
                "        \"432009563252850708\": {\n" +
                "            \"username\": \"DiceyDragon\",\n" +
                "            \"platform\": \"epic\",\n" +
                "            \"discord_user\": \"DiceyDragon\",\n" +
                "            \"points\": 351\n" +
                "        },\n" +
                "        \"239282034361630722\": {\n" +
                "            \"username\": \"SeriouslyAurora\",\n" +
                "            \"platform\": \"epic\",\n" +
                "            \"discord_user\": \"Aurora!\",\n" +
                "            \"points\": 400\n" +
                "        },\n" +
                "        \"302132752285958146\": {\n" +
                "            \"username\": \"jfmachine6\",\n" +
                "            \"platform\": \"epic\",\n" +
                "            \"discord_user\": \"jfmachine\",\n" +
                "            \"points\": 351\n" +
                "        },\n" +
                "        \"359851037609164802\": {\n" +
                "            \"username\": \"Wilson_Family97\",\n" +
                "            \"platform\": \"epic\",\n" +
                "            \"discord_user\": \"Cix\",\n" +
                "            \"points\": 650\n" +
                "        },\n" +
                "        \"582401935769075712\": {\n" +
                "            \"username\": \"Grifysh\",\n" +
                "            \"platform\": \"epic\",\n" +
                "            \"discord_user\": \"Grifysh\",\n" +
                "            \"points\": 901\n" +
                "        },\n" +
                "        \"316454940128051201\": {\n" +
                "            \"username\": \"mcnonutt\",\n" +
                "            \"platform\": \"epic\",\n" +
                "            \"discord_user\": \"McNoNutt9090\",\n" +
                "            \"points\": 450\n" +
                "        },\n" +
                "        \"393920734885314561\": {\n" +
                "            \"username\": \"setakS\",\n" +
                "            \"platform\": \"epic\",\n" +
                "            \"discord_user\": \"Skates\",\n" +
                "            \"points\": 300\n" +
                "        },\n" +
                "        \"153903466060840963\": {\n" +
                "            \"username\": \"Gradster13\",\n" +
                "            \"platform\": \"epic\",\n" +
                "            \"discord_user\": \"Gradster13\",\n" +
                "            \"points\": 250\n" +
                "        },\n" +
                "        \"539921283857645579\": {\n" +
                "            \"username\": \"DocPhilthy\",\n" +
                "            \"platform\": \"epic\",\n" +
                "            \"discord_user\": \"Josh.\",\n" +
                "            \"points\": 1002\n" +
                "        },\n" +
                "        \"185131834135412736\": {\n" +
                "            \"username\": \"lowkangaroo557\",\n" +
                "            \"platform\": \"epic\",\n" +
                "            \"discord_user\": \"Badibicho\",\n" +
                "            \"points\": 1203\n" +
                "        },\n" +
                "        \"100715871781011456\": {\n" +
                "            \"username\": \"Levellogs\",\n" +
                "            \"platform\": \"epic\",\n" +
                "            \"discord_user\": \"Commy Manifesto of Logarithms\",\n" +
                "            \"points\": 150\n" +
                "        },\n" +
                "        \"405528235816779776\": {\n" +
                "            \"username\": \"SolvedSphinx126\",\n" +
                "            \"platform\": \"epic\",\n" +
                "            \"discord_user\": \"SolvedSphinx126\",\n" +
                "            \"points\": 300\n" +
                "        },\n" +
                "        \"436242913819885580\": {\n" +
                "            \"username\": \"sstewy\",\n" +
                "            \"platform\": \"epic\",\n" +
                "            \"discord_user\": \"Eric.\",\n" +
                "            \"points\": 550\n" +
                "        },\n" +
                "        \"410941343674662924\": {\n" +
                "            \"username\": \"CristianOBE\",\n" +
                "            \"platform\": \"epic\",\n" +
                "            \"discord_user\": \"Cristian1\",\n" +
                "            \"points\": 600\n" +
                "        },\n" +
                "        \"434774655564382219\": {\n" +
                "            \"username\": \"poolyy2\",\n" +
                "            \"platform\": \"epic\",\n" +
                "            \"discord_user\": \"pooly\",\n" +
                "            \"points\": 450\n" +
                "        },\n" +
                "        \"754523800980422687\": {\n" +
                "            \"username\": \"The.King.Koda\",\n" +
                "            \"platform\": \"epic\",\n" +
                "            \"discord_user\": \"Dakota\",\n" +
                "            \"points\": 300\n" +
                "        },\n" +
                "        \"607005871209185280\": {\n" +
                "            \"username\": \"XpRt_-_Quad\",\n" +
                "            \"platform\": \"epic\",\n" +
                "            \"discord_user\": \"Vale.\",\n" +
                "            \"points\": 501\n" +
                "        },\n" +
                "        \"403002308679565314\": {\n" +
                "            \"username\": \"Data_Light\",\n" +
                "            \"platform\": \"epic\",\n" +
                "            \"discord_user\": \"Data_Light\",\n" +
                "            \"points\": 150\n" +
                "        },\n" +
                "        \"744758489230082198\": {\n" +
                "            \"username\": \"SoccerZeus19\",\n" +
                "            \"platform\": \"epic\",\n" +
                "            \"discord_user\": \"cole\",\n" +
                "            \"points\": 250\n" +
                "        },\n" +
                "        \"269614780896116739\": {\n" +
                "            \"username\": \"FlameeeeeeeRL\",\n" +
                "            \"platform\": \"steam\",\n" +
                "            \"discord_user\": \"Flame\",\n" +
                "            \"points\": 150\n" +
                "        }\n" +
                "    }\n" +
                "}";

        String json2 = "{\"result\":{\"price\":56240.6},\"allowance\":{\"cost\":0.005,\"remaining\":9.995,\"upgrade\":\"For unlimited API access, create an account at https://cryptowat.ch\"}}";

        JSON realJSON = new JSON(json);
        System.out.println(realJSON);
        for (int lcv = 0; lcv < 128; lcv++)
            System.out.print("-");
        System.out.println();

        System.out.println(new JSON(json2));

        System.out.println(realJSON.get("banned"));

    }
}

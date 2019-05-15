package paoo;

import paoo.core.Application;
import paoo.core.json.JsonObject;
import paoo.core.json.JsonParser;
import paoo.game.Game;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
          Application game = new Game();
          game.run();
//         try {
//             String fileString = new String(Files.readAllBytes(Paths.get("Backup.json")));
//             JsonObject game = JsonParser.parse(fileString);
//             System.out.println(game);
//         } catch(IOException e) {
//         }
//            JsonObject jsonObject = JsonObject.build()
//                .addAttribute("name", "Andrei")
//                .addAttribute("age", 25)
//                .addAttribute("weight", 2.35f)
//                .addAttribute("friend", JsonObject.build()
//                          .addAttribute("name", "Anthony")
//                          .addAttribute("age", 12)
//                          .addAttribute("happiness", 9.3f)
//                          .getObject())
//                .getObject();
//
//        String json = "{\n" +
//                      "    \"name\": \"Andrew\"\n" +
//                      "    \"age\": 25\n" +
//                      "    \"happiness\": 2.5\n" +
//                      "    \"friend\": {\n" +
//                      "        \"name\": \"Anthony\"\n" +
//                      "        \"age\": 12\n" +
//                      "        \"friend\": {\n" +
//                      "            \"name\": \"George\"\n" +
//                      "            \"age\": 12\n" +
//                      "            \"happiness\": 9.3\n" +
//                      "        }\n" +
//                      "        \"happiness\": 9.3\n" +
//                      "    }\n" +
//                      "    \"otherfriend\": {\n" +
//                      "        \"name\": \"Anthony\"\n" +
//                      "        \"age\": 12\n" +
//                      "        \"happiness\": 9.3\n" +
//                      "    }\n" +
//                      "}";
//        JsonObject object = JsonParser.parse(json);
//        System.out.println(object);
    }
}

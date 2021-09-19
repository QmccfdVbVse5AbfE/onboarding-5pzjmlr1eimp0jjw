package edu.brown.cs.student.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.lang.*;
import java.util.*;
import java.util.HashMap;
import java.util.Iterator;

import com.google.common.collect.ImmutableMap;

import freemarker.template.Configuration;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import spark.ExceptionHandler;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Spark;
import spark.TemplateViewRoute;
import spark.template.freemarker.FreeMarkerEngine;
import java.util.LinkedHashMap;

/**
 * The Main class of our project. This is where execution begins.
 */
public final class Main {

  // use port 4567 by default when running server
  private static final int DEFAULT_PORT = 4567;
  private MathBot _mb;
  private List _curData;

  /**
   * The initial method called when execution begins.
   *
   * @param args An array of command line arguments
   */
  public static void main(String[] args) {
    new Main(args).run();
  }

  private String[] args;

  private Main(String[] args) {
    this.args = args;
    _mb = new MathBot();
  }

  private void run() {
    // set up parsing of command line flags
    OptionParser parser = new OptionParser();

    // "./run --gui" will start a web server
    parser.accepts("gui");

    // use "--port <n>" to specify what port on which the server runs
    parser.accepts("port").withRequiredArg().ofType(Integer.class)
        .defaultsTo(DEFAULT_PORT);

    OptionSet options = parser.parse(args);
    if (options.has("gui")) {
      runSparkServer((int) options.valueOf("port"));
    }

    try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
      String input;
      double ans = 0.0;
      while ((input = br.readLine()) != null) {

        try {
          input = input.trim();
          String[] arguments = input.split(" ");

          if(arguments[0].equals("add")){
            double n1 = Double.parseDouble(arguments[1]);
            double n2 = Double.parseDouble(arguments[2]);
            ans = _mb.add(n1, n2);
            System.out.println(ans);
          }
          else if (arguments[0].equals("subtract")){
            double n1 = Double.parseDouble(arguments[1]);
            double n2 = Double.parseDouble(arguments[2]);
            ans = _mb.subtract(n1, n2);
            System.out.println(ans);
          }
          else if (arguments[0].equals("stars")){
            File file = new File(arguments[1]);
            _curData = this.stars(file);
          }
          else if ((_curData != null) && (arguments[0].equals("naive_neighbors"))){
            this.naive_neighbors(_curData, arguments);
          }
          else {
            System.out.println("ERROR: Invalid Command");
          }

        } catch (Exception e) {
          // e.printStackTrace();
          System.out.println("ERROR: We couldn't process your input");
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("ERROR: Invalid input for REPL");
    }

  }

  private static FreeMarkerEngine createEngine() {
    Configuration config = new Configuration(Configuration.VERSION_2_3_0);

    // this is the directory where FreeMarker templates are placed
    File templates = new File("src/main/resources/spark/template/freemarker");
    try {
      config.setDirectoryForTemplateLoading(templates);
    } catch (IOException ioe) {
      System.out.printf("ERROR: Unable use %s for template loading.%n",
          templates);
      System.exit(1);
    }
    return new FreeMarkerEngine(config);
  }

  private void runSparkServer(int port) {
    // set port to run the server on
    Spark.port(port);

    // specify location of static resources (HTML, CSS, JS, images, etc.)
    Spark.externalStaticFileLocation("src/main/resources/static");

    // when there's a server error, use ExceptionPrinter to display error on GUI
    Spark.exception(Exception.class, new ExceptionPrinter());

    // initialize FreeMarker template engine (converts .ftl templates to HTML)
    FreeMarkerEngine freeMarker = createEngine();

    // setup Spark Routes
    Spark.get("/", new MainHandler(), freeMarker);
  }

  /**
   * Display an error page when an exception occurs in the server.
   */
  private static class ExceptionPrinter implements ExceptionHandler<Exception> {
    @Override
    public void handle(Exception e, Request req, Response res) {
      // status 500 generally means there was an internal server error
      res.status(500);

      // write stack trace to GUI
      StringWriter stacktrace = new StringWriter();
      try (PrintWriter pw = new PrintWriter(stacktrace)) {
        pw.println("<pre>");
        e.printStackTrace(pw);
        pw.println("</pre>");
      }
      res.body(stacktrace.toString());
    }
  }

  /**
   * A handler to serve the site's main page.
   *
   * @return ModelAndView to render.
   * (main.ftl).
   */
  private static class MainHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) {
      // this is a map of variables that are used in the FreeMarker template
      Map<String, Object> variables = ImmutableMap.of("title",
          "Go go GUI");

      return new ModelAndView(variables, "main.ftl");
    }
  }
  public List<star> stars(File file) {

    String line;
    List<star> strArr = new ArrayList<star>();
    try {

      BufferedReader fileR = new BufferedReader(new FileReader(file));
      int counter = 0;
      while ((line = fileR.readLine()) != null) {
        System.out.println(line);
        line = line.trim();
        String[] strs = line.split(",");

        if (counter >= 1) {
          star newStar = new star(Integer.parseInt(strs[0]), strs[1], Double.parseDouble(strs[2]),
              Double.parseDouble(strs[3]), Double.parseDouble(strs[4]));
          strArr.add(newStar);
        }
        counter++;
      }

    } catch (Exception e) {
      e.printStackTrace();
    } return strArr;
  }

  public void naive_neighbors(List<star> strArrList, String[] arguments){

      int index;
      HashMap<star, Double> result = new HashMap<>();

      if(Integer.parseInt(arguments[1]) == 0){
        return;
      }

      if(arguments.length == 3){

        String removedQuotes;
        removedQuotes = this.removeQuotes(arguments[2]);
        arguments[2] = removedQuotes;
        index = this.findIndex(arguments[2], strArrList);

        if(!this.exceptionHandling(strArrList, arguments[2])){
          return;
        } else if (strArrList.size() == 1){
          return;
        }

        for(star s: strArrList){
          double temp;
          temp = this.findDis(strArrList.get(index), s);
          result.put(s, temp);
          s.setDis(temp);

        }

      } else if(arguments.length == 5){

        for(star s: strArrList){
          double temp2;
          temp2 = this.approxDis(s, Double.parseDouble(arguments[2]), Double.parseDouble(arguments[3]), Double.parseDouble(arguments[4]));
          result.put(s, temp2);
          s.setDis(temp2);

        }

      } else {
          System.out.println("Invalid Input: Please follow the format");
          return;
        }

      HashMapSort HMS = new HashMapSort(result);
      Iterator<Map.Entry<star, Double>> hmIterator = HMS.hashSort();

      int i = 0;

      while((hmIterator.hasNext()) && (i < Integer.parseInt(arguments[1]))){
        star curStar = hmIterator.next().getKey();
        if(arguments.length == 3) {
          if (!curStar.getName().equals(arguments[2])){
            System.out.println(curStar.getID());
            i++;
          }
        } else {
          System.out.println(curStar.getID());
          i++;
        }
      }
    }
  public double approxDis(star s1, Double x, Double y, Double z){
    double ans = 0.0;
    ans = Math.pow((s1.getX() - x),2) + Math.pow((s1.getY() - y),2) + Math.pow((s1.getZ() - z),2);
    return ans;
  }

  public double findDis(star s1, star s2){
    double ans = 0.0;
    ans = Math.pow((s1.getX() - s2.getX()),2) + Math.pow((s1.getY() - s2.getY()),2) + Math.pow((s1.getZ() - s2.getZ()),2);
    return ans;
  }

  public int findIndex(String target, List<star> st){
    int index = -1;
    for (star str: st){
      if(str.getName().toString().equals(target.toString())){
        index = st.indexOf(str);
      }
    } return index;
  }

  public boolean exceptionHandling(List<star> strList, String targetName){
    boolean exist = false;
    for(star str: strList){
      if(str.getName().equals(targetName)){
        exist = true;
      }
    } return exist;
  }

  public String removeQuotes(String inputStr){
    String returnStr;
    returnStr = inputStr.replace("\"", "");
    return returnStr;
  }
}

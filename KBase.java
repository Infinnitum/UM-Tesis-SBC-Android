package edu.tesis.matias.tesis2015;

import android.app.Activity;
import android.util.Log;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class KBase extends Activity {
    private String rem;
    private ArrayList<Rule> rules;
    private ArrayList<Prompt> prompts;
    private ArrayList<Atributo> goals;
    private ArrayList<Atributo> atributos;
    private String search_url = "https://www.google.com.ar/search?q={SEARCH}";

    public static int MINCF = 70;


    public KBase () {
        this.rem = null;
        this.rules = new ArrayList<Rule>();
        this.prompts = new ArrayList<Prompt>();
        this.goals = new ArrayList<Atributo>();
        this.atributos = new ArrayList<Atributo>();
    }
    public KBase (String rem, ArrayList<Rule> rules, ArrayList<Prompt> prompts, ArrayList<Atributo> goals, ArrayList<Atributo> atributos) {
        this.rem = rem;
        this.rules = rules;
        this.prompts = prompts;
        this.goals = goals;
        this.atributos = atributos;
    }


    public void addFile(StringBuilder text) {
        Rule regla = new Rule();
        boolean ruleFlag = false;
        String ruleFlagParte = "";
        Prompt prompt = new Prompt();
        boolean promptFlag = false;
        boolean promptFlagQ = false;

        for (String s : text.toString().replace("'","\"").split("\\n")) {
            if (!s.equals("")) {
                Log.i("TEST", "- ".concat(s));

                if (s.startsWith("REM ")) {
                    ruleFlag = false;
                    promptFlag = false;
                    promptFlagQ = false;
                    if (this.rem == null) {
                        this.rem = s.split("REM ")[1].trim().toUpperCase();
                    }
                }
                else if (s.startsWith("RULE ")) {
                    ruleFlag = true;
                    promptFlag = false;
                    promptFlagQ = false;
                    regla = new Rule();
                    this.rules.add(regla);
                    regla.setName(s.split("RULE ")[1].replace("[", "").replace("]", "").trim().toUpperCase());
                }
                else if (s.startsWith("PROMPT ")) {
                    ruleFlag = false;
                    promptFlag = true;
                    promptFlagQ = true;
                    prompt = new Prompt();
                    this.prompts.add(prompt);
                    prompt.setAtributo(this.findAtributos(s.split("PROMPT ")[1].replace("[","").split("]")[0].trim().toUpperCase()));
                    prompt.setType(s.split("PROMPT ")[1].replace("[","").split("] ")[1].concat(" ").split(" ")[0].trim().toUpperCase());
                    //if (s.split("PROMPT ")[1].split("] ")[1].concat(" .").split(" ")[1].trim().toUpperCase() == "CF")
                    if (s.trim().toUpperCase().endsWith("CF"))
                        prompt.setCf(true);
                    //Log.i("TEST", "CF : ".concat(s.split("PROMPT ")[1].split("] ")[1].concat(" ").split(" ")[1].trim().toUpperCase()));
                }
                else if (s.startsWith("GOAL ")) {
                    ruleFlag = false;
                    promptFlag = false;
                    promptFlagQ = false;
                    this.goals.add(this.findAtributos(s.split("GOAL ")[1].replace("[","").split("]")[0].trim().toUpperCase()));
                    Log.i("TEST", "GOAL: ".concat(s.split("GOAL ")[1].replace("[","").split("]")[0].trim().toUpperCase()));
                }
                else if (s.startsWith("DEFAULT ")) {
                    ruleFlag = false;
                    promptFlag = false;
                    promptFlagQ = false;

                    this.findAtributos(s.split("DEFAULT ")[1].replace("[","").split("]")[0].trim().toUpperCase()).setDefvalue(s.split(" = ")[1].replace("\"","").replace("'","").trim());

                    Log.i("TESTDEFAULT", s);
                    Log.i("TESTDEFAULT", s.split("DEFAULT ")[1].replace("[","").split("]")[0].trim());
                    Log.i("TESTDEFAULT", s.split(" = ")[1].replace("\"","").replace("'","").trim());
                }
                else if (s.startsWith("MINCF ")) {
                    ruleFlag = false;
                    promptFlag = false;
                    promptFlagQ = false;

                    KBase.MINCF = Integer.decode(s.split("MINCF ")[1]);
                }
                else if (s.startsWith("MAXVALS ") ||
                        s.startsWith("FORMAT ") ||
                        s.startsWith("HYPERLINK ") ||
                        s.startsWith("INFOLINK ") ||
                        s.startsWith("JSHYPERLINK ") ||
                        s.startsWith("NOSHOW ") ||
                        s.startsWith("PARAM ") ||
                        s.startsWith("TRANSLATE ") ||
                        s.startsWith("KBCHAIN ") ||
                        s.startsWith("NOCHAIN ")) {
                    ruleFlag = false;
                    promptFlag = false;
                    promptFlagQ = false;
                }
                else if (ruleFlag) {
                    s = s.trim().toUpperCase();
                    if (s.startsWith("IF ")) ruleFlagParte="IF";
                    else if (s.startsWith("THEN ")) ruleFlagParte="THEN";
                    else if (s.startsWith("ELSE ")) ruleFlagParte="ELSE";

                    if (s.endsWith(" AND")) {
                        s = s.split(" AND")[0];
                        if (regla.getOperador() == null) regla.setOperador("AND");
                    }
                    else if (s.endsWith(" OR")) {
                        s = s.split(" OR")[0];
                        if (regla.getOperador() == null) regla.setOperador("OR");
                    }
                    else {
                        if (regla.getOperador() == null) regla.setOperador("NONE");
                    }

                    if (ruleFlagParte.equals("IF")) {
                        Log.i("TEST", "- ".concat(s));
                        if (s.startsWith("IF ")) regla.addCondicion(this, s.split("IF ")[1]);
                        else regla.addCondicion(this, s);
                        Log.i("TEST", "- ".concat(s));
                    }
                    else if (ruleFlagParte.equals("THEN")) {
                        if (s.startsWith("THEN ")) regla.addResultadoThen(this, s.split("THEN ")[1]);
                        else regla.addResultadoThen(this, s);
                    }
                    else if (ruleFlagParte.equals("ELSE")) {
                        if (s.startsWith("ELSE ")) regla.addResultadoElse(this, s.split("ELSE ")[1]);
                        else regla.addResultadoElse(this, s);
                    }
                }
                else if (promptFlagQ) {
                    prompt.setQuestion(s.replace("\"","").replace("'",""));
                    promptFlagQ = false;
                }
                else if (promptFlag) {
                    prompt.addExtra(s.replace("\"","").replace("'",""));
                }
                //ruleFlag=false;
            }
        }
    }


    public void addFileKX(StringBuilder text) {
        // TODO: Test and Fix KBX files
        boolean atributoFlag = false;
        String name = "";
        ArrayList<String> value = new ArrayList<String>();
        int cf = 100;
        String url = "";

        for (String s : text.toString().replace("'","\"").split("\\n")) {
            if (!s.equals("")) {
                Log.i("TEST", "addFileKX - ".concat(s));

                if (s.startsWith("<atributo>")) {
                    atributoFlag = true;
                }
                else if (s.startsWith("</atributo>")) {
                    atributoFlag = false;

                    Atributo a = this.findAtributos(name);
                    Log.i("TEST", "addFileDAX 11111");
                    Log.i("TEST", name);

                    if (!a.isValid()) {
                        for (String t : value) {
                            a.addValue(t);
                            Log.i("TEST", "addFileDAX values");
                            Log.i("TEST", t);
                        }
                        Log.i("TEST", "addFileDAX 22222");

                        if (!url.equals("")) {
                            a.setUrl(url);
                            Log.i("TEST", a.getUrl());
                            Log.i("TEST", a.getValues().toString());
                            a.addValue("~URL DUMMY~");
                            try {
                                new ArchivoURL().execute(a).get(60, TimeUnit.SECONDS);
                            } catch (Exception e) {
                                Log.i("TEST", e.toString());
                            }
                            Log.i("TEST", a.getValues().toString());
                        }
                        if (a.getValues().get(0).equals("~URL DUMMY~")) {
                            a.setValue(new ArrayList<String>());
                        } else {
                            Log.i("TEST", "addFileDAX 33333");
                            a.setOrigen("KX");
                            a.setCf(cf);
                            findPrompt(a).setUtilizado(true);
                        }
                        url = "";
                        name = "";
                        value = new ArrayList<String>();
                        cf = 100;
                    }
                }
                else if (atributoFlag) {
                    s = s.trim();
                    if (s.startsWith("<nombre>")) name=s.replace("<nombre>","").replace("</nombre>","").trim().toUpperCase();
                    else if (s.startsWith("<valor>")) value.add(s.replace("<valor>","").replace("</valor>","").trim().toUpperCase());
                    else if (s.startsWith("<cf>")) cf=Integer.decode(s.replace("<cf>","").replace("</cf>",""));
                    else if (s.startsWith("<url>")) url=s.replace("<url>","").replace("</url>","");
                }
                else if (s.startsWith("<search_url>")) search_url=s.replace("<search_url>","").replace("</search_url>","");
            }
        }
    }


    public void updateAtributos() {
        for (Rule r : this.rules) {
            r.execute();
        }
    }


    public Prompt nextPrompt() {
        ArrayList<Prompt> p = new ArrayList<Prompt>();
        boolean b = false;
        for (Atributo g : this.goals){
            Log.i("TEST", g.getName());
            if (!g.isValid()) {
                Log.i("TEST","NO VALIDO");
                b = true;
                this.findPrompt(g, p, 0);
            }
        }
        if (b && !p.isEmpty()) {
            Log.i("TEST",p.get(0).getQuestion());
            Log.i("TEST",p.get(0).getAtributo().getName());
            return p.get(0);
        }
        else return null;
    }


    public boolean completeGoals() {
        boolean b = true;
        for (Atributo g : this.goals){
            if (!g.isValid()) {
                b = false;
            }
        }
        return b;
    }

    public String explain() {
        String s = "";
        for (Atributo g : this.goals){
            s = s.concat("*** OBJETIVO  ->  ".concat(g.getName()).concat(" :\n").concat(g.toText()).concat("\n\n"));
        }
        return s;
    }

    public boolean completePrompt() {
        boolean b = true;
        for (Prompt p : this.prompts){
            if (!p.getUtilizado()) {
                b = false;
            }
        }
        return b;
    }

    public String getGoalsTxt() {
        String s = ""; // this.rem.concat("\n");
        Log.i("TEST","START");
        for (Atributo a : this.goals) {
            Log.i("TEST","Atributo ".concat(a.getName()));
            for (String t : a.getValues()) {
                Log.i("TEST","String ".concat(t));
                s = s.concat("> ");
                s = s.concat(a.getName());
                s = s.concat(":\n\t");
                s = s.concat(t.replace("\\N","\n\t").replace(" & ","\n\t"));
                s = s.concat("\n");
            }
            //s = s.concat("--------------------------------\n");
        }
        Log.i("TEST","END");
        return s;
    }

    public String getGoalsSearch() {
        String s = "";
        for (Atributo a : this.goals) {
            for (String t : a.getValues()) {
                if (!s.isEmpty()) s = s.concat(" or ");
                s = s.concat(t);
            }
        }
        return s;
    }

    public Prompt findPrompt(Atributo atr) {
        Prompt p = null;
        for (Prompt o : this.prompts){
            if (!o.getUtilizado() && o.getAtributo().equals(atr)) p=o;
        }
        return p;
    }

    public void findPrompt(Atributo atr, ArrayList<Prompt> pro, int lv) {
        Prompt p;
        if (lv < 50) {
            for (Prompt r : this.prompts) {
                if (lv == 0 && !r.getUtilizado() && r.getAtributo().equals(atr)) {
                    Log.i("TEST-KBase.findPrompt", r.getQuestion());
                    pro.add(r);
                }
            }

            for (Rule r : this.rules) {
                //Log.i("TEST-KBase.findPrompt", "1");
                if (r.hasResultado(atr)) {
                    //Log.i("TEST-KBase.findPrompt", "2");
                    if (r.isTrue()) {
                        //Log.i("TEST-KBase.findPrompt", "3");
                        for (Condicion c : r.getCondicion()) {
                            //Log.i("TEST-KBase.findPrompt", c.getAtributo().getName());
                            p = findPrompt(c.getAtributo());
                            if (p == null) {
                                //Log.i("TEST-QWE", "KBase.findPrompt1");
                                //if (c.getAtributo2() != c.getAtributo()) this.findPrompt(c.getAtributo2(), pro, lv+1);
                                //Log.i("TEST-QWE", "KBase.findPrompt");
                                this.findPrompt(c.getAtributo(), pro, lv+1);
                            } else {
                                boolean b = true;
                                for (Prompt o : pro){
                                    if (o.equals(p)) b = false;
                                }
                                if (b) pro.add(p);
                            }

                            if (c.getAtributo2() != c.getAtributo()) {
                                p = findPrompt(c.getAtributo2());
                                if (p == null) {
                                    //Log.i("TEST-QWE", "KBase.findPrompt2");
                                    this.findPrompt(c.getAtributo2(), pro, lv + 1);
                                } else {
                                    boolean b = true;
                                    for (Prompt o : pro) {
                                        if (o.equals(p)) b = false;
                                    }
                                    if (b) pro.add(p);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void anularPregunta(String s) {
        String p = s.split(" ~ ")[0];
        Log.i("TEST","KBASE.anularPregunta");
        Log.i("TEST",p);
        String v = s.split(" ~ ")[0];

        for (Prompt r : this.prompts) {
            if (r.getQuestion().equals(p)) {
                Log.i("TEST",r.getQuestion());
                Log.i("TEST",v);
                r.borrar(v);
            }
        }

        for (Atributo r : this.goals) {
            if (r.getOrigen().equals("RULE")) {
                r.borrar();
            }
        }

        for (Atributo r : this.atributos) {
            if (r.getOrigen().equals("RULE")) {
                r.borrar();
            }
        }
    }

    public ArrayList<String> listarRespuesta() {
        ArrayList<String> s = new ArrayList<String>();
        boolean b;
        String u;
        String v;

        for (Prompt r : this.prompts) {
            if (r.getUtilizado()) {
                u = r.getQuestion();
                b = true;
                v = "";
                for (String t : r.getAtributo().getValues()) {
                    if (b) {
                        v = t;
                        b = false;
                    } else {
                        v = v.concat("; ");
                        v = v.concat(t);
                    }
                }
                u = u.concat(" ~ ");
                u = u.concat(v);
                s.add(u);
            }
        }
        return s;
    }

    public String toTexto() {
        String s = this.rem.concat("\n");
        for (Rule r : this.rules) {
            s = s.concat("REGLA: ");
            s = s.concat(r.getName());
            s = s.concat(" *** ");
            for (Condicion c : r.getCondicion()) {
                s = s.concat(c.getAtributo().getName());
                s = s.concat(" # ");
            }
            s = s.concat("\n");
        }
        for (Prompt p : this.prompts) {
            s = s.concat("PROMPT: ");
            s = s.concat(p.getAtributo().getName());
            s = s.concat(" *** ");
            s = s.concat(p.getQuestion());
            s = s.concat("\n");
        }
        for (Atributo a : this.goals) {
            s = s.concat("GOAL: ");
            s = s.concat(a.getName());
            s = s.concat("\n");
        }
        s = s.concat("\n");
        s = s.concat("----------");
        s = s.concat("\n");
        for (Atributo a : this.atributos) {
            s = s.concat("* ");
            s = s.concat(a.getName());
            s = s.concat("\n");
        }
        return s;
    }


    public String getRem() {
        return this.rem;
    }
    public void setRem(String rem) {
        this.rem = rem;
    }

    public ArrayList<Rule> getRules() {
        return this.rules;
    }
    public void setRules(ArrayList<Rule> rules) {
        this.rules = rules;
    }
    public void addRules(Rule rules) {
        this.rules.add(rules);
    }

    public ArrayList<Prompt> getPrompts() {
        return this.prompts;
    }
    public void setPrompts(ArrayList<Prompt> prompts) {
        this.prompts = prompts;
    }
    public void addPrompts(Prompt prompts) {
        this.prompts.add(prompts);
    }

    public ArrayList<Atributo> getGoals() {
        return goals;
    }
    public void setGoals(ArrayList<Atributo> goals) {
        this.goals = goals;
    }
    public void addGoals(Atributo goals) {
        this.goals.add(goals);
    }

    public ArrayList<Atributo> getAtributos() {
        return this.atributos;
    }
    public void setAtributos(ArrayList<Atributo> atributos) {
        this.atributos = atributos;
    }
    public void addAtributos(Atributo atributos) {
        this.atributos.add(atributos);
    }
    public Atributo findAtributos(String atributo) {
        Atributo r = null;
        if (!this.atributos.isEmpty()){
            for (Atributo a : this.atributos) {
                if (a.getName().equals(atributo)) r = a;
            }
        }
        if (r == null) {
            r = new Atributo(atributo);
            this.atributos.add(r);
        }
        return r;
    }

    public String getSearch_url() {
        return this.search_url;
    }
    public void setSearch_url(String url) {
        this.search_url = url;
    }

}

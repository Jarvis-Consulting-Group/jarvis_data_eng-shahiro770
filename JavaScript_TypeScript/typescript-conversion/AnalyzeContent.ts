/// <reference lib="es2015" />

module ts {
    type Output = {
        type: string;
        cssTargets?: { [key: string]: number };
        lineNumber?: number;
        tags?: { [key: string]: number };
    };

    function analyzeContent(text: string): Output {
        let output: Output = {
            type: ""
        };

        output.type = getType(text);

        if (output.type == "TEXT") {
            const lines = text.split("\n");
            output.lineNumber = lines.length;
        } else if (output.type == "CSS") {
            const cssTargets: { [key: string]: number } = {};
            const properties = text.split(" ");
            for (const p of properties) {
                if (p.includes("{")) {
                    let key : string = p.split("{")[0];
                    cssTargets[key] = cssTargets[key] || 0;
                    cssTargets[key]++;
                }
            }
            output.cssTargets = cssTargets;
        } else if (output.type == "HTML") {
            const tags: { [key: string]: number } = {};
            const allTags = text.split(">");
            for (const t of allTags) {
                if (t.includes("</") && !t.includes("<!")) {
                    const key = t.slice(2);
                    tags[key] = tags[key] || 0;
                    tags[key]++;
                }
            }
            output.tags = tags;
        }

        return output;
    }

    function getType(text: string): string {
        const words = text.split(" ");
        let type = "TEXT";

        for (const word of words) {
            if (word.match("<(?:(?!!)).*>")) {
                type = "HTML";
                break;
            } else if (word.match(".+\\{.+}")) {
                type = "CSS";
                break;
            }
        }

        return type;
    }

    console.log(analyzeContent("this is a test\nSeems to work"));
    console.log(analyzeContent("body{blabla} a{color:#fff} a{ padding:0}"));
    console.log(analyzeContent("<!DOCTYPE=html><!--><html><div></div><div></div></html>"));
    console.log(analyzeContent("<!-->"));
}

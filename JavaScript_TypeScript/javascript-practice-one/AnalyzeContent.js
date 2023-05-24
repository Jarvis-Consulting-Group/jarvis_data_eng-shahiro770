function analyzeContent(text) {
    let output = {};

    output.type = getType(text);

    if (output.type == "TEXT") {
        lines = text.split("\n");
        output.lineNumber = lines.length;
    }
    else if (output.type == "CSS") {
        cssTargets = {};
        const properties = text.split(" ");
        for (p of properties) {
            if (p.includes("{")) {
                key = p.split("{")[0];
                cssTargets[key] = cssTargets[key] || 0;
                cssTargets[key]++;
            }
        }
        output.cssTargets = cssTargets;
    }
    else if (output.type == "HTML") {
        tags = {};
        const allTags = text.split(">");
        for (t of allTags) {
            if (t.includes("</") && !t.includes("<!")) {
                key = t.slice(2);
                tags[key] = tags[key] || 0;
                tags[key]++;
            }
        }
        output.tags = tags;
    }

    return output;
}

function getType(text) {
    const words = text.split(" ");
    let type = "TEXT";

    for (const word of words) {
        if (word.match("<(?:(?!!)).*>")) {
            type = "HTML"
            break;
        }
        else if (word.match(".+\{.+\}")) {
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
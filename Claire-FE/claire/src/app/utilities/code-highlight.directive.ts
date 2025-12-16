import { Directive, ElementRef, Input, OnChanges, SimpleChanges } from '@angular/core';
import hljs from 'highlight.js';

@Directive({
  selector: '[appAutoHighlight]',
  standalone: true
})
export class CodeHighlightDirective implements OnChanges {

  @Input('appAutoHighlight') code: string | null | undefined = '';

  constructor(private el: ElementRef) {}

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['code']) {
        this.highlight();
    }
  }

  private highlight() {
    if (!this.code) {
        this.el.nativeElement.innerHTML = '';
        return;
    }

    const rawCode = this.code;

    const detection = hljs.highlightAuto(rawCode);
    const language = detection.language || 'plaintext';

    const formattedCode = this.universalFormat(rawCode, language);

    const finalHighlight = hljs.highlight(formattedCode, { language: language });

    this.el.nativeElement.innerHTML = finalHighlight.value;
  }

  private universalFormat(code: string, language: string): string {
    const lowerLang = language.toLowerCase();
    
    if (['xml', 'html', 'svg', 'mathml'].includes(lowerLang)) {
       return this.formatXml(code);
    }
    if (['python', 'yaml', 'yml', 'bash', 'sh'].includes(lowerLang)) {
        return code.trim();
    }
    return this.formatCStyle(code);
  }

  private formatCStyle(code: string): string {
    let output = '';
    let indentLevel = 0;
    const indentChar = '    ';
    let inString = false;
    let stringChar = '';
    let inParens = 0;

    const cleanCode = code.replace(/\s+/g, ' ').trim();

    for (let i = 0; i < cleanCode.length; i++) {
      const char = cleanCode[i];
      const prevChar = i > 0 ? cleanCode[i - 1] : '';
      const nextChar = i < cleanCode.length - 1 ? cleanCode[i + 1] : '';

      if ((char === '"' || char === "'") && prevChar !== '\\') {
        if (!inString) {
          inString = true;
          stringChar = char;
        } else if (char === stringChar) {
          inString = false;
        }
        output += char;
        continue;
      }
      if (inString) {
        output += char;
        continue;
      }

      if (char === '(') { inParens++; output += char; continue; }
      if (char === ')') { inParens = Math.max(0, inParens - 1); output += char; continue; }

      switch (char) {
        case '{':
          output += ' {\n';
          indentLevel++;
          output += indentChar.repeat(indentLevel);
          break;

        case '}':
          output = output.trimEnd();
          indentLevel = Math.max(0, indentLevel - 1);
          output += '\n' + indentChar.repeat(indentLevel) + '}';
          if (nextChar !== ';' && nextChar !== ',' && nextChar !== ')') {
            output += '\n' + indentChar.repeat(indentLevel);
          }
          break;

        case ';':
          output += ';';
          if (inParens === 0) {
            output += '\n' + indentChar.repeat(indentLevel);
          } else {
            output += ' ';
          }
          break;

        case ',':
          output += ', ';
          break;

        default:
          if (char === ' ') {
            if (indentLevel === 0) {
              if (output === '' || output.endsWith('\n')) {
              } else {
                output += char;
              }
            } 
            else if (output.endsWith(indentChar.repeat(indentLevel))) {

            } 
            else {
              output += char;
            }
          } else {
            output += char;
          }
      }
    }
    return output.replace(/\n\s*\n/g, '\n').trim();
  }

  private formatXml(code: string): string {
    let formatted = '';
    let indent = 0;
    const pad = '  ';
    code.split(/>\s*</).forEach(node => {
        if (node.match(/^\/\w/)) indent = Math.max(0, indent - 1);
        formatted += pad.repeat(indent) + '<' + node + '>\r\n';
        if (node.match(/^<?\w[^>]*[^\/]$/) && !node.startsWith("input") && !node.startsWith("img")) indent++;
    });
    return formatted.substring(1, formatted.length - 3);
  }
}
import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'removeHyphenPipe'
})
export class RemoveHyphenPipePipe implements PipeTransform {

  transform(value: string): string {
    return value.replace(/-/g, " ");
  }

}

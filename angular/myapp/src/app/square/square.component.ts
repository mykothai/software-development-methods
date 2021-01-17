import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-square',
  template: `
    <button>{{ value }}</button>
  `,
  styles: [
  ]
})
export class SquareComponent  {

    @Input() value: 'X' | 'O';

}

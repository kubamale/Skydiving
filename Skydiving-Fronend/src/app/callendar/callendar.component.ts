import { Component, OnInit } from '@angular/core';
import { Router, NavigationExtras } from '@angular/router';
import { CallendarDateModel } from 'src/shared/calendarDate';
import { DepartureService } from '../departure.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-callendar',
  templateUrl: './callendar.component.html',
  styleUrls: ['./callendar.component.css']
})
export class CallendarComponent implements OnInit{
  callendarTile: CallendarDateModel[] = [];
  curentDate: Date = new Date();
  weeksToDisplay!: number;
  startIndex!: number;
  weeksRows: number[] = [];
  startDay!: number;
  curentMonth!: string;
  curentYear!: string;
  daysOfWeekMap: Map<string, number> = new Map([
    ["Monday", 1],
    ["Tuesday", 2],
    ["Wednesday", 3],
    ["Thursday", 4],
    ["Friday", 5],
    ["Saturday", 6],
    ["Sunday", 7]
  ]);

  constructor(private router: Router, private departureService: DepartureService, private _snackBar: MatSnackBar){}
  
  ngOnInit(): void {
    this.setCallendar(this.curentDate);
    this.updateCallendarTile();
  }

  updateCallendarTile(): void{
    this.departureService.getDeparturesDates(this.callendarTile[0].date, this.callendarTile[this.callendarTile.length-1].date)
    .subscribe(data => {
      var departureDates = data as string[];
      for (let tile of this.callendarTile){
        if(departureDates.indexOf(tile.date) !== -1){
          tile.isDeparture = true;
        }
        
      }
    });

  }

  prevMonth(): void {
    this.curentDate.setMonth(this.curentDate.getMonth() - 1);
    this.setCallendar(this.curentDate);
    this.updateCallendarTile();
  }

  nextMonth(): void {
    this.curentDate.setMonth(this.curentDate.getMonth() + 1);
    this.setCallendar(this.curentDate);
    this.updateCallendarTile();
  }

  setCallendar(date: Date): void {
    let daysToadd = this.daysOfWeekMap.get(this.getFirstDayOfMonthWithDayOfWeek(date));
    
    if (daysToadd !== undefined) {
      this.startDay = daysToadd;
      this.weeksToDisplay = ((this.getDaysInMonth(date.getFullYear(), date.getMonth()) + daysToadd -1)/7);
    }
    this.setRows(this.weeksToDisplay);

    this.setDaysToDisplay(date);
    this.curentMonth = date.toLocaleString('default', {'month' : 'long'});
    this.curentYear = date.toLocaleString('default', {'year' : 'numeric'});
  }

  setRows(rows: number): void {
    this.weeksRows = [];
    for (let i = 0; i < rows; i++) {
      this.weeksRows.push(i);
    }

    
  }

  getFirstDayOfMonthWithDayOfWeek(date: Date): string {
    // Clone the input date to avoid modifying it
    const firstDay = new Date(date);
    
    // Set the day of the month to 1
    firstDay.setDate(1);
  
    // Define an array of weekday names
    const weekdays = ["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"];
  
    // Get the day of the week for the first day of the month and return it
    const dayOfWeek = weekdays[firstDay.getDay()];
    
    return dayOfWeek;
  }

  getDaysInMonth(year: number, month: number): number {
    // Create a new Date object for the specified year and month
    // Note: JavaScript months are 0-based, so January is 0, February is 1, and so on
    const date = new Date(year, month, 1);
  
    // Move to the next month and subtract one day to get the last day of the specified month
    date.setMonth(date.getMonth() + 1);
    date.setDate(date.getDate() - 1);
  
    // Get the day of the month, which gives the number of days in the month
    return date.getDate();
  }

  setDaysToDisplay(date: Date): void {
    let res: number[] = [];
    this.callendarTile =[]; 
    let diff = this.startDay-2;
    let prev = this.getDaysInMonth(date.getFullYear(), date.getMonth() -1);
    let now = this.getDaysInMonth(date.getFullYear(), date.getMonth());
    let nowIndex = 1;
    for (let i = 0; i < Math.ceil(this.weeksToDisplay)*7; i++) {
      let newDate = new Date();
      newDate.setFullYear(date.getFullYear());
      newDate.setMonth(date.getMonth());
      if (i < this.startDay -1){
        res.push(prev - diff);
        diff--;
        newDate.setMonth(date.getMonth()-1);
        this.callendarTile.push({
          date: (prev - diff).toString() + '-' + (newDate.getMonth()+1).toString() + '-' + newDate.getFullYear().toString(),
          day: (prev - diff).toString(),
          month: (newDate.getMonth()+1).toString(),
          isDeparture:false
        })
        
      }
      else if( i < this.startDay + now -1){
        res.push(nowIndex);
        
        this.callendarTile.push({
          date: (nowIndex).toString() + '-' +(date.getMonth()+1).toString() + '-' + date.getFullYear().toString(),
          day: (nowIndex).toString(),
          month: (date.getMonth()+1).toString(),
          isDeparture:false
        })
        nowIndex++;
      }
      else{
        res.push(i-this.startDay - now +2);
        newDate.setMonth(date.getMonth() +1);
        this.callendarTile.push({
          date: (i-this.startDay - now +2).toString() + '-' + (newDate.getMonth()+1).toString() + '-' + newDate.getFullYear().toString(),
          day: (i-this.startDay - now +2).toString(),
          month: (newDate.getMonth()+1).toString(),
          isDeparture:false
        })
      }
    }
  }

  showDepartures(col: number){
    if(!this.callendarTile[col].isDeparture){
      this._snackBar.open('No departurea on that day!', 'close');
      return;
    }

    var extras: NavigationExtras = {
     queryParams:{
      date: this.callendarTile[col].date
     }
    };

    this.router.navigate(['/departures'], extras);
  }
}

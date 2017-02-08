//EventEmitter.publish({ eventType: 'AddToCart' }); in child .then()

//    EventEmitter.subscribe(this, this.onCartEvent, 'AddToCart'); in father cdm
//    EventEmitter.subscribe(this, this.onCartEvent, 'RemoveFromCart'); in father componentDidMount

//    EventEmitter.unsubscribe(this);


var EventEmitter = function() {
  this._subscribers = [];

  this.subscribe = function(sub, handler, eventType) {
    this._subscribers.push({
      sub: sub,
      handler: handler,
      eventType: eventType
    });
  },

  this.unsubscribe = function(subToUnsubscribe) {
    this._subscribers = this._subscribers.filter(function(sub) { return sub.sub !== subToUnsubscribe; });
  },

  this.publish = function(event) {
    this._subscribers.filter(function (sub) { return sub.eventType === event.eventType || !sub.eventType; })
      .forEach(function(sub) {
        sub.handler(event);
      });
  };
};

window.EventEmitter = new EventEmitter();

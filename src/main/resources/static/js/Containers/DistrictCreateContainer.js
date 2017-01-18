var DistrictCreateContainer = React.createClass({
getInitialState: function() {
  return {
    name: '',
  };
},
  onHandleNameChange : function(event){
    this.set.state({name: event.target.value});
  },
  

  render: function() {
    return (
      <div>
        <DistrictCreateEditComponent
        onHandleNameChange={this.onHandleNameChange} />
      </div>
    );
  }

});

window.DistrictCreateContainer = DistrictCreateContainer;

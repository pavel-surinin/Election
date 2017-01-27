var CountyCreateContainer = React.createClass({
  getInitialState: function() {
    return {
      name : '',
      nameClone : false,
      nameErrorMesages : [],
    };
  },

  onHandleNameChange : function(event){
    this.setState({name : event.target.value});
  },
  onHandleSubmit : function(event){
    event.preventDefault();
    var countyName = {name: this.state.name.trim()};
    var self = this;
    //validation
    var nameErrorMesages = [];
    if(!validation.checkCountyName(this.state.name)) {nameErrorMesages.push('Pavadinimą gali sudaryti tik raidės, - , ir tarpai');}
    if(!validation.checkMax(this.state.name,35)) {nameErrorMesages.push('Pavadinimas negali būti ilgesnis, nei 35 simbolių');}
    if(!validation.checkMin(this.state.name,4)) {nameErrorMesages.push('Pavadinimas negali būti trumpesnis, nei 4 simboliai');}
    if (nameErrorMesages.length == 0) {
      axios
      .post('/county', countyName)
      .then(function(response){
        console.log(response);
        self.context.router.push('/admin/county');
      })
      .catch(function(err){
        console.error('CountyCreateContainer.onHandleSubmit.axios', err);
        self.setState({nameClone : true});
      });
    } else {
      this.setState({nameErrorMesages : nameErrorMesages});
    }
  },
  render: function() {
    return (
      <CountyCreateEditFormComponent
       onHandleNameChange={this.onHandleNameChange}
       name={this.state.name}
       onHandleSubmit={this.onHandleSubmit}
       nameClone={this.state.nameClone}
       action='Sukurti'
       nameErrorMesages={this.state.nameErrorMesages}
       />
    );
  }
});
CountyCreateContainer.contextTypes = {
  router: React.PropTypes.object.isRequired,
};
window.CountyCreateContainer = CountyCreateContainer ;

var CountyEditContainer = React.createClass({
  getInitialState: function() {
    return {
      name : '',
      nameBefore : '',
      nameClone : false,
      nameErrorMesages : [],
      isLoading : true,
      countyDetails : [],
    };
  },

  componentWillMount: function() {
      var self = this;
      axios
      .get('/county/' + this.props.params.id)
      .then(function(response){
        self.setState({
          name: response.data.name,
          nameBefore: response.data.name,
          isLoading : false,
        });
      })
      .catch(function(err){
        console.error('CountyCreateContainer.componentWillMount.axios', err);
      });
    },
  onHandleNameChange : function(event){
      this.setState({name : event.target.value});
    },
    onHandleSubmit : function(event){
      event.preventDefault();
      var countyName = this.state.name.trim();
      var self = this;
      //validation
      var nameErrorMesages = [];
      if(!validation.checkCountyName(this.state.name)) {nameErrorMesages.push('Pavadinimą gali sudaryti tik raidės, - , ir tarpai');}
      if(!validation.checkMax(this.state.name,35)) {nameErrorMesages.push('Pavadinimas negali būti ilgesnis, nei 35 simbolių');}
      if(!validation.checkMin(this.state.name,4)) {nameErrorMesages.push('Pavadinimas negali būti trumpesnis, nei 4 simboliai');}
      if (nameErrorMesages.length == 0) {
        axios
        .post('/county', {
              id: self.props.params.id,
              name: countyName
            })
        .then(function(response){
          console.log(response);
          self.context.router.push('/admin/county?succesCreateText=Apygardos pavadinimas pakeistas iš ' + self.state.nameBefore + ' į ' + self.state.name);
        })
        .catch(function(err){
          console.error('CountyEditContainer.onHandleSubmit.axios', err);
          self.setState({nameClone : true});
        });
      } else {
        this.setState({nameErrorMesages : nameErrorMesages});
      }
    },
    render: function() {
      if (this.state.isLoading) {
        return (
          <div>
            <img src='./Images/loading.gif'/>
          </div>
        );
      } else {
        return (
          <CountyCreateEditFormComponent
             onHandleNameChange={this.onHandleNameChange}
             name={this.state.name}
             id={this.state.id}
            onHandleSubmit={this.onHandleSubmit}
             nameClone={this.state.nameClone}
             action='Atnaujinti'
             nameErrorMesages={this.state.nameErrorMesages}
           />
      );
    }
  },
});
CountyEditContainer.contextTypes = {
  router: React.PropTypes.object.isRequired,
};
window.CountyEditContainer = CountyEditContainer ;

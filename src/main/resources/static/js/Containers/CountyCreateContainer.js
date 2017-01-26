var CountyCreateContainer = React.createClass({
  getInitialState: function() {
    return {
      name : '',
      nameClone : false,
    };
  },

  onHandleNameChange : function(event){
    this.setState({name : event.target.value});
  },
  onHandleSubmit : function(event){
    var countyName = {name: this.state.name.trim()};
    var self = this;
    event.preventDefault();
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
  },
  render: function() {
    return (
      <CountyCreateEditFormComponent
       onHandleNameChange={this.onHandleNameChange}
       name={this.state.name}
       onHandleSubmit={this.onHandleSubmit}
       nameClone={this.state.nameClone}
       action='Sukurti'
       />
    );
  }
});
CountyCreateContainer.contextTypes = {
  router: React.PropTypes.object.isRequired,
};
window.CountyCreateContainer = CountyCreateContainer ;

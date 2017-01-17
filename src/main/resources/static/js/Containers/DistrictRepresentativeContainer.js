var DistrictRepresentativeContainer = React.createClass({

  getInitialState: function() {
    return {
      representativesList : [],
      isLoading : true,
    };
  },

  componentWillMount: function() {
    var self = this;
    axios
      .get('/representative')
      .then(function(response){
        self.setState({
          representativesList :  response.data,
          isLoading : false,
        });
      })
      .catch(function(err){
        console.error('DistrictRepresentativeContainer.componentWillMount.axios.get.representative', err);
      });
  },

  render: function() {
    if (this.state.isLoading) {
      return (
        <div>
          <img src='https://upload.wikimedia.org/wikipedia/commons/b/b1/Loading_icon.gif'/>
        </div>
      );
    } else {
      console.log(this.state.representativesList);
      return (
        <DistrictRepresentativeComponent representativesList={this.state.representativesList}/>
      );
    }

  }

});

window.DistrictRepresentativeContainer = DistrictRepresentativeContainer;
